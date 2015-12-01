package com.facebook;

public enum SessionState {
    CREATED(Category.CREATED_CATEGORY),
    CREATED_TOKEN_LOADED(Category.CREATED_CATEGORY),
    OPENING(Category.CREATED_CATEGORY),
    OPENED(Category.OPENED_CATEGORY),
    OPENED_TOKEN_UPDATED(Category.OPENED_CATEGORY),
    CLOSED_LOGIN_FAILED(Category.CLOSED_CATEGORY),
    CLOSED(Category.CLOSED_CATEGORY);
    
    private final Category category;

    private enum Category {
        public static final Category CLOSED_CATEGORY = null;
        public static final Category CREATED_CATEGORY = null;
        private static final /* synthetic */ Category[] ENUM$VALUES = null;
        public static final Category OPENED_CATEGORY = null;

        public static Category valueOf(String str) {
            return (Category) Enum.valueOf(Category.class, str);
        }

        public static Category[] values() {
            Object obj = ENUM$VALUES;
            int length = obj.length;
            Object obj2 = new Category[length];
            System.arraycopy(obj, 0, obj2, 0, length);
            return obj2;
        }

        private Category(String str, int i) {
        }

        static {
            CREATED_CATEGORY = new Category("CREATED_CATEGORY", 0);
            OPENED_CATEGORY = new Category("OPENED_CATEGORY", 1);
            CLOSED_CATEGORY = new Category("CLOSED_CATEGORY", 2);
            ENUM$VALUES = new Category[]{CREATED_CATEGORY, OPENED_CATEGORY, CLOSED_CATEGORY};
        }
    }

    private SessionState(Category category) {
        this.category = category;
    }

    public boolean isOpened() {
        return this.category == Category.OPENED_CATEGORY;
    }

    public boolean isClosed() {
        return this.category == Category.CLOSED_CATEGORY;
    }
}
