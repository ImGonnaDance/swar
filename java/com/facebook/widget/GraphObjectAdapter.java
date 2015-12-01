package com.facebook.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.facebook.FacebookException;
import com.facebook.Resource;
import com.facebook.android.R;
import com.facebook.internal.ImageDownloader;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageRequest.Builder;
import com.facebook.internal.ImageRequest.Callback;
import com.facebook.internal.ImageResponse;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObject.Factory;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.o;
import org.json.JSONObject;

class GraphObjectAdapter<T extends GraphObject> extends BaseAdapter implements SectionIndexer {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$facebook$widget$GraphObjectAdapter$SectionAndItem$Type = null;
    static final /* synthetic */ boolean $assertionsDisabled = (!GraphObjectAdapter.class.desiredAssertionStatus() ? true : $assertionsDisabled);
    private static final int ACTIVITY_CIRCLE_VIEW_TYPE = 2;
    private static final int DISPLAY_SECTIONS_THRESHOLD = 1;
    private static final int GRAPH_OBJECT_VIEW_TYPE = 1;
    private static final int HEADER_VIEW_TYPE = 0;
    private static final String ID = "id";
    private static final int MAX_PREFETCHED_PICTURES = 20;
    private static final String NAME = "name";
    private static final String PICTURE = "picture";
    private Context context;
    private GraphObjectCursor<T> cursor;
    private DataNeededListener dataNeededListener;
    private boolean displaySections;
    private Filter<T> filter;
    private Map<String, T> graphObjectsById = new HashMap();
    private Map<String, ArrayList<T>> graphObjectsBySection = new HashMap();
    private String groupByField;
    private final LayoutInflater inflater;
    private OnErrorListener onErrorListener;
    private final Map<String, ImageRequest> pendingRequests = new HashMap();
    private Map<String, ImageResponse> prefetchedPictureCache = new HashMap();
    private ArrayList<String> prefetchedProfilePictureIds = new ArrayList();
    private List<String> sectionKeys = new ArrayList();
    private boolean showCheckbox;
    private boolean showPicture;
    private List<String> sortFields;

    public interface DataNeededListener {
        void onDataNeeded();
    }

    interface Filter<T> {
        boolean includeItem(T t);
    }

    private interface ItemPicture extends GraphObject {
        ItemPictureData getData();
    }

    private interface ItemPictureData extends GraphObject {
        String getUrl();
    }

    public interface OnErrorListener {
        void onError(GraphObjectAdapter<?> graphObjectAdapter, FacebookException facebookException);
    }

    public static class SectionAndItem<T extends GraphObject> {
        public T graphObject;
        public String sectionKey;

        public enum Type {
            GRAPH_OBJECT,
            SECTION_HEADER,
            ACTIVITY_CIRCLE
        }

        public SectionAndItem(String sectionKey, T graphObject) {
            this.sectionKey = sectionKey;
            this.graphObject = graphObject;
        }

        public Type getType() {
            if (this.sectionKey == null) {
                return Type.ACTIVITY_CIRCLE;
            }
            if (this.graphObject == null) {
                return Type.SECTION_HEADER;
            }
            return Type.GRAPH_OBJECT;
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$facebook$widget$GraphObjectAdapter$SectionAndItem$Type() {
        int[] iArr = $SWITCH_TABLE$com$facebook$widget$GraphObjectAdapter$SectionAndItem$Type;
        if (iArr == null) {
            iArr = new int[Type.values().length];
            try {
                iArr[Type.ACTIVITY_CIRCLE.ordinal()] = 3;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Type.GRAPH_OBJECT.ordinal()] = GRAPH_OBJECT_VIEW_TYPE;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Type.SECTION_HEADER.ordinal()] = ACTIVITY_CIRCLE_VIEW_TYPE;
            } catch (NoSuchFieldError e3) {
            }
            $SWITCH_TABLE$com$facebook$widget$GraphObjectAdapter$SectionAndItem$Type = iArr;
        }
        return iArr;
    }

    public GraphObjectAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public List<String> getSortFields() {
        return this.sortFields;
    }

    public void setSortFields(List<String> sortFields) {
        this.sortFields = sortFields;
    }

    public String getGroupByField() {
        return this.groupByField;
    }

    public void setGroupByField(String groupByField) {
        this.groupByField = groupByField;
    }

    public boolean getShowPicture() {
        return this.showPicture;
    }

    public void setShowPicture(boolean showPicture) {
        this.showPicture = showPicture;
    }

    public boolean getShowCheckbox() {
        return this.showCheckbox;
    }

    public void setShowCheckbox(boolean showCheckbox) {
        this.showCheckbox = showCheckbox;
    }

    public DataNeededListener getDataNeededListener() {
        return this.dataNeededListener;
    }

    public void setDataNeededListener(DataNeededListener dataNeededListener) {
        this.dataNeededListener = dataNeededListener;
    }

    public OnErrorListener getOnErrorListener() {
        return this.onErrorListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    public GraphObjectCursor<T> getCursor() {
        return this.cursor;
    }

    public boolean changeCursor(GraphObjectCursor<T> cursor) {
        if (this.cursor == cursor) {
            return $assertionsDisabled;
        }
        if (this.cursor != null) {
            this.cursor.close();
        }
        this.cursor = cursor;
        rebuildAndNotify();
        return true;
    }

    public void rebuildAndNotify() {
        rebuildSections();
        notifyDataSetChanged();
    }

    public void prioritizeViewRange(int firstVisibleItem, int lastVisibleItem, int prefetchBuffer) {
        if (lastVisibleItem >= firstVisibleItem && this.sectionKeys.size() != 0) {
            int i;
            SectionAndItem<T> sectionAndItem;
            for (i = lastVisibleItem; i >= 0; i--) {
                sectionAndItem = getSectionAndItem(i);
                if (sectionAndItem.graphObject != null) {
                    ImageRequest request = (ImageRequest) this.pendingRequests.get(getIdOfGraphObject(sectionAndItem.graphObject));
                    if (request != null) {
                        ImageDownloader.prioritizeRequest(request);
                    }
                }
            }
            int start = Math.max(HEADER_VIEW_TYPE, firstVisibleItem - prefetchBuffer);
            int end = Math.min(lastVisibleItem + prefetchBuffer, getCount() - 1);
            ArrayList<T> graphObjectsToPrefetchPicturesFor = new ArrayList();
            for (i = start; i < firstVisibleItem; i += GRAPH_OBJECT_VIEW_TYPE) {
                sectionAndItem = getSectionAndItem(i);
                if (sectionAndItem.graphObject != null) {
                    graphObjectsToPrefetchPicturesFor.add(sectionAndItem.graphObject);
                }
            }
            for (i = lastVisibleItem + GRAPH_OBJECT_VIEW_TYPE; i <= end; i += GRAPH_OBJECT_VIEW_TYPE) {
                sectionAndItem = getSectionAndItem(i);
                if (sectionAndItem.graphObject != null) {
                    graphObjectsToPrefetchPicturesFor.add(sectionAndItem.graphObject);
                }
            }
            Iterator it = graphObjectsToPrefetchPicturesFor.iterator();
            while (it.hasNext()) {
                GraphObject graphObject = (GraphObject) it.next();
                URI uri = getPictureUriOfGraphObject(graphObject);
                String id = getIdOfGraphObject(graphObject);
                boolean alreadyPrefetching = this.prefetchedProfilePictureIds.remove(id);
                this.prefetchedProfilePictureIds.add(id);
                if (!alreadyPrefetching) {
                    downloadProfilePicture(id, uri, null);
                }
            }
        }
    }

    protected String getSectionKeyOfGraphObject(T graphObject) {
        String result = null;
        if (this.groupByField != null) {
            result = (String) graphObject.getProperty(this.groupByField);
            if (result != null && result.length() > 0) {
                result = result.substring(HEADER_VIEW_TYPE, GRAPH_OBJECT_VIEW_TYPE).toUpperCase();
            }
        }
        return result != null ? result : i.a;
    }

    protected CharSequence getTitleOfGraphObject(T graphObject) {
        return (String) graphObject.getProperty(NAME);
    }

    protected CharSequence getSubTitleOfGraphObject(T t) {
        return null;
    }

    protected URI getPictureUriOfGraphObject(T graphObject) {
        String uri = null;
        String o = graphObject.getProperty(PICTURE);
        if (o instanceof String) {
            uri = o;
        } else if (o instanceof JSONObject) {
            ItemPictureData data = ((ItemPicture) Factory.create((JSONObject) o).cast(ItemPicture.class)).getData();
            if (data != null) {
                uri = data.getUrl();
            }
        }
        if (uri != null) {
            try {
                return new URI(uri);
            } catch (URISyntaxException e) {
            }
        }
        return null;
    }

    protected View getSectionHeaderView(String sectionHeader, View convertView, ViewGroup parent) {
        TextView result = (TextView) convertView;
        if (result == null) {
            result = (TextView) this.inflater.inflate(Resource.R(this.context, "R.layout.com_facebook_picker_list_section_header"), null);
        }
        result.setText(sectionHeader);
        return result;
    }

    protected View getGraphObjectView(T graphObject, View convertView, ViewGroup parent) {
        View result = convertView;
        if (result == null) {
            result = createGraphObjectView(graphObject);
        }
        populateGraphObjectView(result, graphObject);
        return result;
    }

    private View getActivityCircleView(View convertView, ViewGroup parent) {
        View result = convertView;
        if (result == null) {
            result = this.inflater.inflate(Resource.R(this.context, "R.layout.com_facebook_picker_activity_circle_row"), null);
        }
        ((ProgressBar) result.findViewById(Resource.R(this.context, "R.id.com_facebook_picker_row_activity_circle"))).setVisibility(HEADER_VIEW_TYPE);
        return result;
    }

    protected int getGraphObjectRowLayoutId(T t) {
        return R.layout.com_facebook_picker_list_row;
    }

    protected int getDefaultPicture() {
        return R.drawable.com_facebook_profile_default_icon;
    }

    protected View createGraphObjectView(T graphObject) {
        View result = this.inflater.inflate(getGraphObjectRowLayoutId(graphObject), null);
        ViewStub checkboxStub = (ViewStub) result.findViewById(Resource.R(this.context, "R.id.com_facebook_picker_checkbox_stub"));
        if (checkboxStub != null) {
            if (getShowCheckbox()) {
                updateCheckboxState((CheckBox) checkboxStub.inflate(), $assertionsDisabled);
            } else {
                checkboxStub.setVisibility(8);
            }
        }
        ViewStub profilePicStub = (ViewStub) result.findViewById(Resource.R(this.context, "R.id.com_facebook_picker_profile_pic_stub"));
        if (getShowPicture()) {
            ((ImageView) profilePicStub.inflate()).setVisibility(HEADER_VIEW_TYPE);
        } else {
            profilePicStub.setVisibility(8);
        }
        return result;
    }

    protected void populateGraphObjectView(View view, T graphObject) {
        String id = getIdOfGraphObject(graphObject);
        view.setTag(id);
        CharSequence title = getTitleOfGraphObject(graphObject);
        TextView titleView = (TextView) view.findViewById(Resource.R(this.context, "R.id.com_facebook_picker_title"));
        if (titleView != null) {
            titleView.setText(title, BufferType.SPANNABLE);
        }
        CharSequence subtitle = getSubTitleOfGraphObject(graphObject);
        TextView subtitleView = (TextView) view.findViewById(Resource.R(this.context, "R.id.picker_subtitle"));
        if (subtitleView != null) {
            if (subtitle != null) {
                subtitleView.setText(subtitle, BufferType.SPANNABLE);
                subtitleView.setVisibility(HEADER_VIEW_TYPE);
            } else {
                subtitleView.setVisibility(8);
            }
        }
        if (getShowCheckbox()) {
            updateCheckboxState((CheckBox) view.findViewById(Resource.R(this.context, "R.id.com_facebook_picker_checkbox")), isGraphObjectSelected(id));
        }
        if (getShowPicture()) {
            URI pictureURI = getPictureUriOfGraphObject(graphObject);
            if (pictureURI != null) {
                ImageView profilePic = (ImageView) view.findViewById(Resource.R(this.context, "R.id.com_facebook_picker_image"));
                if (this.prefetchedPictureCache.containsKey(id)) {
                    ImageResponse response = (ImageResponse) this.prefetchedPictureCache.get(id);
                    profilePic.setImageBitmap(response.getBitmap());
                    profilePic.setTag(response.getRequest().getImageUri());
                    return;
                }
                downloadProfilePicture(id, pictureURI, profilePic);
            }
        }
    }

    String getIdOfGraphObject(T graphObject) {
        if (graphObject.asMap().containsKey(ID)) {
            Object obj = graphObject.getProperty(ID);
            if (obj instanceof String) {
                return (String) obj;
            }
        }
        throw new FacebookException("Received an object without an ID.");
    }

    boolean filterIncludesItem(T graphObject) {
        return (this.filter == null || this.filter.includeItem(graphObject)) ? true : $assertionsDisabled;
    }

    Filter<T> getFilter() {
        return this.filter;
    }

    void setFilter(Filter<T> filter) {
        this.filter = filter;
    }

    boolean isGraphObjectSelected(String graphObjectId) {
        return $assertionsDisabled;
    }

    void updateCheckboxState(CheckBox checkBox, boolean graphObjectSelected) {
    }

    String getPictureFieldSpecifier() {
        ImageView picture = (ImageView) createGraphObjectView(null).findViewById(Resource.R(this.context, "R.id.com_facebook_picker_image"));
        if (picture == null) {
            return null;
        }
        LayoutParams layoutParams = picture.getLayoutParams();
        Object[] objArr = new Object[ACTIVITY_CIRCLE_VIEW_TYPE];
        objArr[HEADER_VIEW_TYPE] = Integer.valueOf(layoutParams.height);
        objArr[GRAPH_OBJECT_VIEW_TYPE] = Integer.valueOf(layoutParams.width);
        return String.format(Locale.US, "picture.height(%d).width(%d)", objArr);
    }

    private boolean shouldShowActivityCircleCell() {
        return (this.cursor == null || !this.cursor.areMoreObjectsAvailable() || this.dataNeededListener == null || isEmpty()) ? $assertionsDisabled : true;
    }

    private void rebuildSections() {
        boolean z = true;
        this.sectionKeys = new ArrayList();
        this.graphObjectsBySection = new HashMap();
        this.graphObjectsById = new HashMap();
        this.displaySections = $assertionsDisabled;
        if (this.cursor != null && this.cursor.getCount() != 0) {
            int objectsAdded = HEADER_VIEW_TYPE;
            this.cursor.moveToFirst();
            do {
                T graphObject = this.cursor.getGraphObject();
                if (filterIncludesItem(graphObject)) {
                    objectsAdded += GRAPH_OBJECT_VIEW_TYPE;
                    String sectionKeyOfItem = getSectionKeyOfGraphObject(graphObject);
                    if (!this.graphObjectsBySection.containsKey(sectionKeyOfItem)) {
                        this.sectionKeys.add(sectionKeyOfItem);
                        this.graphObjectsBySection.put(sectionKeyOfItem, new ArrayList());
                    }
                    ((List) this.graphObjectsBySection.get(sectionKeyOfItem)).add(graphObject);
                    this.graphObjectsById.put(getIdOfGraphObject(graphObject), graphObject);
                }
            } while (this.cursor.moveToNext());
            if (this.sortFields != null) {
                final Collator collator = Collator.getInstance();
                for (List<T> section : this.graphObjectsBySection.values()) {
                    Collections.sort(section, new Comparator<GraphObject>() {
                        public int compare(GraphObject a, GraphObject b) {
                            return GraphObjectAdapter.compareGraphObjects(a, b, GraphObjectAdapter.this.sortFields, collator);
                        }
                    });
                }
            }
            Collections.sort(this.sectionKeys, Collator.getInstance());
            if (this.sectionKeys.size() <= GRAPH_OBJECT_VIEW_TYPE || objectsAdded <= GRAPH_OBJECT_VIEW_TYPE) {
                z = $assertionsDisabled;
            }
            this.displaySections = z;
        }
    }

    SectionAndItem<T> getSectionAndItem(int position) {
        if (this.sectionKeys.size() == 0) {
            return null;
        }
        String sectionKey = null;
        T graphObject = null;
        List<T> section;
        if (this.displaySections) {
            for (String key : this.sectionKeys) {
                int position2 = position - 1;
                if (position == 0) {
                    sectionKey = key;
                    position = position2;
                    break;
                }
                section = (List) this.graphObjectsBySection.get(key);
                if (position2 < section.size()) {
                    sectionKey = key;
                    GraphObject graphObject2 = (GraphObject) section.get(position2);
                    position = position2;
                    break;
                }
                position = position2 - section.size();
            }
        } else {
            sectionKey = (String) this.sectionKeys.get(HEADER_VIEW_TYPE);
            section = (List) this.graphObjectsBySection.get(sectionKey);
            if (position >= 0 && position < section.size()) {
                graphObject = (GraphObject) ((ArrayList) this.graphObjectsBySection.get(sectionKey)).get(position);
            } else if ($assertionsDisabled || (this.dataNeededListener != null && this.cursor.areMoreObjectsAvailable())) {
                return new SectionAndItem(null, null);
            } else {
                throw new AssertionError();
            }
        }
        if (sectionKey != null) {
            return new SectionAndItem(sectionKey, graphObject);
        }
        throw new IndexOutOfBoundsException("position");
    }

    int getPosition(String sectionKey, T graphObject) {
        int position = HEADER_VIEW_TYPE;
        boolean found = $assertionsDisabled;
        for (String key : this.sectionKeys) {
            if (this.displaySections) {
                position += GRAPH_OBJECT_VIEW_TYPE;
            }
            if (key.equals(sectionKey)) {
                found = true;
                break;
            }
            position += ((ArrayList) this.graphObjectsBySection.get(key)).size();
        }
        if (!found) {
            return -1;
        }
        if (graphObject == null) {
            return position - (this.displaySections ? GRAPH_OBJECT_VIEW_TYPE : HEADER_VIEW_TYPE);
        }
        Iterator it = ((ArrayList) this.graphObjectsBySection.get(sectionKey)).iterator();
        while (it.hasNext()) {
            if (Factory.hasSameId((GraphObject) it.next(), graphObject)) {
                return position;
            }
            position += GRAPH_OBJECT_VIEW_TYPE;
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.sectionKeys.size() == 0 ? true : $assertionsDisabled;
    }

    public int getCount() {
        int i = HEADER_VIEW_TYPE;
        if (this.sectionKeys.size() == 0) {
            return HEADER_VIEW_TYPE;
        }
        if (this.displaySections) {
            i = this.sectionKeys.size();
        }
        for (List<T> section : this.graphObjectsBySection.values()) {
            i += section.size();
        }
        if (shouldShowActivityCircleCell()) {
            return i + GRAPH_OBJECT_VIEW_TYPE;
        }
        return i;
    }

    public boolean areAllItemsEnabled() {
        return this.displaySections;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isEnabled(int position) {
        return getSectionAndItem(position).getType() == Type.GRAPH_OBJECT ? true : $assertionsDisabled;
    }

    public Object getItem(int position) {
        SectionAndItem<T> sectionAndItem = getSectionAndItem(position);
        return sectionAndItem.getType() == Type.GRAPH_OBJECT ? sectionAndItem.graphObject : null;
    }

    public long getItemId(int position) {
        SectionAndItem<T> sectionAndItem = getSectionAndItem(position);
        if (!(sectionAndItem == null || sectionAndItem.graphObject == null)) {
            String id = getIdOfGraphObject(sectionAndItem.graphObject);
            if (id != null) {
                try {
                    return Long.parseLong(id);
                } catch (NumberFormatException e) {
                }
            }
        }
        return 0;
    }

    public int getViewTypeCount() {
        return 3;
    }

    public int getItemViewType(int position) {
        switch ($SWITCH_TABLE$com$facebook$widget$GraphObjectAdapter$SectionAndItem$Type()[getSectionAndItem(position).getType().ordinal()]) {
            case GRAPH_OBJECT_VIEW_TYPE /*1*/:
                return GRAPH_OBJECT_VIEW_TYPE;
            case ACTIVITY_CIRCLE_VIEW_TYPE /*2*/:
                return HEADER_VIEW_TYPE;
            case o.c /*3*/:
                return ACTIVITY_CIRCLE_VIEW_TYPE;
            default:
                throw new FacebookException("Unexpected type of section and item.");
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        SectionAndItem<T> sectionAndItem = getSectionAndItem(position);
        switch ($SWITCH_TABLE$com$facebook$widget$GraphObjectAdapter$SectionAndItem$Type()[sectionAndItem.getType().ordinal()]) {
            case GRAPH_OBJECT_VIEW_TYPE /*1*/:
                return getGraphObjectView(sectionAndItem.graphObject, convertView, parent);
            case ACTIVITY_CIRCLE_VIEW_TYPE /*2*/:
                return getSectionHeaderView(sectionAndItem.sectionKey, convertView, parent);
            case o.c /*3*/:
                if ($assertionsDisabled || (this.cursor.areMoreObjectsAvailable() && this.dataNeededListener != null)) {
                    this.dataNeededListener.onDataNeeded();
                    return getActivityCircleView(convertView, parent);
                }
                throw new AssertionError();
            default:
                throw new FacebookException("Unexpected type of section and item.");
        }
    }

    public Object[] getSections() {
        if (this.displaySections) {
            return this.sectionKeys.toArray();
        }
        return new Object[HEADER_VIEW_TYPE];
    }

    public int getPositionForSection(int section) {
        if (!this.displaySections) {
            return HEADER_VIEW_TYPE;
        }
        section = Math.max(HEADER_VIEW_TYPE, Math.min(section, this.sectionKeys.size() - 1));
        if (section < this.sectionKeys.size()) {
            return getPosition((String) this.sectionKeys.get(section), null);
        }
        return HEADER_VIEW_TYPE;
    }

    public int getSectionForPosition(int position) {
        SectionAndItem<T> sectionAndItem = getSectionAndItem(position);
        if (sectionAndItem == null || sectionAndItem.getType() == Type.ACTIVITY_CIRCLE) {
            return HEADER_VIEW_TYPE;
        }
        return Math.max(HEADER_VIEW_TYPE, Math.min(this.sectionKeys.indexOf(sectionAndItem.sectionKey), this.sectionKeys.size() - 1));
    }

    public List<T> getGraphObjectsById(Collection<String> ids) {
        Set<String> idSet = new HashSet();
        idSet.addAll(ids);
        ArrayList<T> result = new ArrayList(idSet.size());
        for (String id : idSet) {
            GraphObject graphObject = (GraphObject) this.graphObjectsById.get(id);
            if (graphObject != null) {
                result.add(graphObject);
            }
        }
        return result;
    }

    private void downloadProfilePicture(final String profileId, URI pictureURI, final ImageView imageView) {
        if (pictureURI != null) {
            boolean prefetching = imageView == null ? true : $assertionsDisabled;
            if (prefetching || !pictureURI.equals(imageView.getTag())) {
                if (!prefetching) {
                    imageView.setTag(profileId);
                    imageView.setImageResource(getDefaultPicture());
                }
                ImageRequest newRequest = new Builder(this.context.getApplicationContext(), pictureURI).setCallerTag(this).setCallback(new Callback() {
                    public void onCompleted(ImageResponse response) {
                        GraphObjectAdapter.this.processImageResponse(response, profileId, imageView);
                    }
                }).build();
                this.pendingRequests.put(profileId, newRequest);
                ImageDownloader.downloadAsync(newRequest);
            }
        }
    }

    private void callOnErrorListener(Exception exception) {
        if (this.onErrorListener != null) {
            if (!(exception instanceof FacebookException)) {
                exception = new FacebookException((Throwable) exception);
            }
            this.onErrorListener.onError(this, (FacebookException) exception);
        }
    }

    private void processImageResponse(ImageResponse response, String graphObjectId, ImageView imageView) {
        this.pendingRequests.remove(graphObjectId);
        if (response.getError() != null) {
            callOnErrorListener(response.getError());
        }
        if (imageView == null) {
            if (response.getBitmap() != null) {
                if (this.prefetchedPictureCache.size() >= MAX_PREFETCHED_PICTURES) {
                    this.prefetchedPictureCache.remove((String) this.prefetchedProfilePictureIds.remove(HEADER_VIEW_TYPE));
                }
                this.prefetchedPictureCache.put(graphObjectId, response);
            }
        } else if (graphObjectId.equals(imageView.getTag())) {
            Exception error = response.getError();
            Bitmap bitmap = response.getBitmap();
            if (error == null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
                imageView.setTag(response.getRequest().getImageUri());
            }
        }
    }

    private static int compareGraphObjects(GraphObject a, GraphObject b, Collection<String> sortFields, Collator collator) {
        for (String sortField : sortFields) {
            String sa = (String) a.getProperty(sortField);
            String sb = (String) b.getProperty(sortField);
            if (sa != null && sb != null) {
                int result = collator.compare(sa, sb);
                if (result != 0) {
                    return result;
                }
            } else if (sa != null || sb != null) {
                return sa == null ? -1 : GRAPH_OBJECT_VIEW_TYPE;
            }
        }
        return HEADER_VIEW_TYPE;
    }
}
