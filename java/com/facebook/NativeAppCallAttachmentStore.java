package com.facebook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import com.com2us.module.manager.ModuleConfig;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public final class NativeAppCallAttachmentStore implements AttachmentDataSource {
    static final String ATTACHMENTS_DIR_NAME = "com.facebook.NativeAppCallAttachmentStore.files";
    private static final String TAG = NativeAppCallAttachmentStore.class.getName();
    private static File attachmentsDirectory;

    interface ProcessAttachment<T> {
        void processAttachment(T t, File file) throws IOException;
    }

    public void addAttachmentsForCall(Context context, UUID callId, Map<String, Bitmap> imageAttachments) {
        Validate.notNull(context, "context");
        Validate.notNull(callId, "callId");
        Validate.containsNoNulls(imageAttachments.values(), "imageAttachments");
        Validate.containsNoNullOrEmpty(imageAttachments.keySet(), "imageAttachments");
        addAttachments(context, callId, imageAttachments, new ProcessAttachment<Bitmap>() {
            public void processAttachment(Bitmap attachment, File outputFile) throws IOException {
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                try {
                    attachment.compress(CompressFormat.JPEG, 100, outputStream);
                } finally {
                    Utility.closeQuietly(outputStream);
                }
            }
        });
    }

    public void addAttachmentFilesForCall(Context context, UUID callId, Map<String, File> mediaAttachmentFiles) {
        Validate.notNull(context, "context");
        Validate.notNull(callId, "callId");
        Validate.containsNoNulls(mediaAttachmentFiles.values(), "mediaAttachmentFiles");
        Validate.containsNoNullOrEmpty(mediaAttachmentFiles.keySet(), "mediaAttachmentFiles");
        addAttachments(context, callId, mediaAttachmentFiles, new ProcessAttachment<File>() {
            public void processAttachment(File attachment, File outputFile) throws IOException {
                Throwable th;
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                FileInputStream inputStream = null;
                try {
                    FileInputStream inputStream2 = new FileInputStream(attachment);
                    try {
                        byte[] buffer = new byte[ModuleConfig.MERCURY_MODULE];
                        while (true) {
                            int len = inputStream2.read(buffer);
                            if (len <= 0) {
                                Utility.closeQuietly(outputStream);
                                Utility.closeQuietly(inputStream2);
                                return;
                            }
                            outputStream.write(buffer, 0, len);
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        inputStream = inputStream2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    Utility.closeQuietly(outputStream);
                    Utility.closeQuietly(inputStream);
                    throw th;
                }
            }
        });
    }

    private <T> void addAttachments(Context context, UUID callId, Map<String, T> attachments, ProcessAttachment<T> processor) {
        File file;
        if (attachments.size() != 0) {
            if (attachmentsDirectory == null) {
                cleanupAllAttachments(context);
            }
            ensureAttachmentsDirectoryExists(context);
            List<File> filesToCleanup = new ArrayList();
            try {
                for (Entry<String, T> entry : attachments.entrySet()) {
                    String attachmentName = (String) entry.getKey();
                    T attachment = entry.getValue();
                    file = getAttachmentFile(callId, attachmentName, true);
                    filesToCleanup.add(file);
                    processor.processAttachment(attachment, file);
                }
            } catch (Throwable exception) {
                Log.e(TAG, "Got unexpected exception:" + exception);
                for (File file2 : filesToCleanup) {
                    try {
                        file2.delete();
                    } catch (Exception e) {
                    }
                }
                throw new FacebookException(exception);
            }
        }
    }

    public void cleanupAttachmentsForCall(Context context, UUID callId) {
        Utility.deleteDirectory(getAttachmentsDirectoryForCall(callId, false));
    }

    public File openAttachment(UUID callId, String attachmentName) throws FileNotFoundException {
        if (Utility.isNullOrEmpty(attachmentName) || callId == null) {
            throw new FileNotFoundException();
        }
        try {
            return getAttachmentFile(callId, attachmentName, false);
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }

    static synchronized File getAttachmentsDirectory(Context context) {
        File file;
        synchronized (NativeAppCallAttachmentStore.class) {
            if (attachmentsDirectory == null) {
                attachmentsDirectory = new File(context.getCacheDir(), ATTACHMENTS_DIR_NAME);
            }
            file = attachmentsDirectory;
        }
        return file;
    }

    File ensureAttachmentsDirectoryExists(Context context) {
        File dir = getAttachmentsDirectory(context);
        dir.mkdirs();
        return dir;
    }

    File getAttachmentsDirectoryForCall(UUID callId, boolean create) {
        if (attachmentsDirectory == null) {
            return null;
        }
        File dir = new File(attachmentsDirectory, callId.toString());
        if (!create || dir.exists()) {
            return dir;
        }
        dir.mkdirs();
        return dir;
    }

    File getAttachmentFile(UUID callId, String attachmentName, boolean createDirs) throws IOException {
        File dir = getAttachmentsDirectoryForCall(callId, createDirs);
        if (dir == null) {
            return null;
        }
        try {
            return new File(dir, URLEncoder.encode(attachmentName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    void cleanupAllAttachments(Context context) {
        Utility.deleteDirectory(getAttachmentsDirectory(context));
    }
}
