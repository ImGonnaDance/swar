package com.com2us.peppermint;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import com.com2us.module.manager.ModuleConfig;
import com.com2us.peppermint.util.PeppermintEncryption;
import com.com2us.peppermint.util.PeppermintLog;
import com.com2us.peppermint.util.PeppermintUtil;
import com.facebook.Session;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ActivityResult {
    private Peppermint a;

    public ActivityResult(Peppermint peppermint) {
        this.a = peppermint;
    }

    public void onActivityResultForCamera(int i, int i2, Intent intent) {
        if (i2 != 0) {
            Intent intent2;
            switch (i) {
                case PeppermintConstant.REQUEST_CODE_PICK_FROM_CAMERA /*827393*/:
                    intent2 = new Intent("com.android.camera.action.CROP");
                    intent2.setDataAndType(this.a.getDialog().getImageCaptureUri(), "image/*");
                    intent2.putExtra("outputX", ModuleConfig.SOCIAL_MEDIA_MOUDLE);
                    intent2.putExtra("outputY", ModuleConfig.SOCIAL_MEDIA_MOUDLE);
                    intent2.putExtra("aspectX", 1);
                    intent2.putExtra("aspectY", 1);
                    intent2.putExtra("scale", true);
                    PeppermintLog.i("CAMERA ExternalStorageState : " + Environment.getExternalStorageState());
                    if ("mounted".equals(Environment.getExternalStorageState())) {
                        this.a.getDialog().setImageOutputUri(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg")));
                        intent2.putExtra("output", this.a.getDialog().getImageOutputUri());
                    }
                    this.a.getMainActivity().startActivityForResult(intent2, PeppermintConstant.REQUEST_CODE_CROP_FROM_CAMERA);
                    return;
                case PeppermintConstant.REQUEST_CODE_CROP_FROM_CAMERA /*827395*/:
                    Bitmap bitmap = null;
                    if (this.a.getDialog().getImageOutputUri() == null) {
                        PeppermintLog.i("onActivityResultForCamera REQUEST_CODE_CROP_FROM_CAMERA mImageOutputUri is null");
                        return;
                    }
                    try {
                        String scheme = this.a.getDialog().getImageOutputUri().getScheme();
                        if (scheme != null && scheme.contains("content")) {
                            bitmap = Media.getBitmap(this.a.getMainActivity().getContentResolver(), this.a.getPeppermintDialog().getImageOutputUri());
                        } else if (scheme != null && scheme.contains("file")) {
                            Options options = new Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(this.a.getDialog().getImageOutputUri().getPath(), options);
                            options.inSampleSize = Math.round(((float) options.outHeight) / 500.0f);
                            options.inJustDecodeBounds = false;
                            bitmap = BitmapFactory.decodeFile(this.a.getDialog().getImageOutputUri().getPath(), options);
                        }
                        if (bitmap == null) {
                            this.a.getDialog().getWebView().loadUrl("javascript:window['native'].getPictureCallback('')");
                            return;
                        }
                        bitmap = PeppermintUtil.getRotatedBitmap(bitmap, PeppermintUtil.getDegreeFromOrientation(new ExifInterface(this.a.getDialog().getImageOutputUri().getPath()).getAttributeInt("Orientation", 1)));
                        if (bitmap.getWidth() > 500) {
                            bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
                        }
                        PeppermintLog.i("REQUEST_CODE_CROP_FROM_CAMERA mImageOutputUri Height : " + bitmap.getHeight());
                        this.a.getDialog().getWebView().loadUrl("javascript:window['native'].getPictureCallback('" + new String(PeppermintEncryption.encodeBase64(PeppermintUtil.getByteArrFromBitmap(bitmap))) + "')");
                        File file = new File(this.a.getDialog().getImageOutputUri().getPath());
                        PeppermintLog.i("file.exists() : " + file.exists());
                        PeppermintLog.i("file.delete() : " + file.delete());
                        return;
                    } catch (FileNotFoundException e) {
                        return;
                    } catch (IOException e2) {
                        return;
                    }
                case PeppermintConstant.REQUEST_CODE_PICK_FROM_ALBUM /*10596354*/:
                    if (intent == null) {
                        try {
                            throw new NullPointerException("onActivityResultForCamera data is null");
                        } catch (NullPointerException e3) {
                            this.a.getDialog().getWebView().loadUrl("javascript:window['native'].getPictureCallback('')");
                            return;
                        }
                    }
                    this.a.getDialog().setImageCaptureUri(intent.getData());
                    intent2 = new Intent("com.android.camera.action.CROP");
                    intent2.setDataAndType(this.a.getDialog().getImageCaptureUri(), "image/*");
                    intent2.putExtra("outputX", ModuleConfig.SOCIAL_MEDIA_MOUDLE);
                    intent2.putExtra("outputY", ModuleConfig.SOCIAL_MEDIA_MOUDLE);
                    intent2.putExtra("aspectX", 1);
                    intent2.putExtra("aspectY", 1);
                    intent2.putExtra("scale", true);
                    PeppermintLog.i("ALBUM ExternalStorageState : " + Environment.getExternalStorageState());
                    if ("mounted".equals(Environment.getExternalStorageState())) {
                        this.a.getDialog().setImageOutputUri(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg")));
                        intent2.putExtra("output", this.a.getDialog().getImageOutputUri());
                    }
                    this.a.getMainActivity().startActivityForResult(intent2, PeppermintConstant.REQUEST_CODE_CROP_FROM_CAMERA);
                    return;
                default:
                    return;
            }
        }
    }

    public void onActivityResultForFacebook(int i, int i2, Intent intent) {
        PeppermintLog.i("onActivityResultForFacebook requestCode=" + i + " resultCode=" + i2 + " data=" + intent);
        switch (i) {
            case Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE /*64206*/:
            case PeppermintConstant.REQUEST_CODE_REAUTH_ACTIVITY /*15336995*/:
                Session activeSession = Session.getActiveSession();
                PeppermintLog.i("onActivityResultForFacebook session=" + activeSession);
                if (activeSession != null) {
                    activeSession.onActivityResult(this.a.getMainActivity(), i, i2, intent);
                    return;
                }
                return;
            default:
                return;
        }
    }
}
