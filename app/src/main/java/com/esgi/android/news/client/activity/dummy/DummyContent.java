package com.esgi.android.news.client.activity.dummy;

import com.esgi.android.news.metier.model.Item;
import com.esgi.android.news.physique.db.dao.ItemDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Item> ITEMS = new ArrayList<Item>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<Long, Item> ITEM_MAP = new HashMap<Long, Item>();

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createItem(i));
        }
    }

    private static void addItem(Item item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    private static Item createItem(int position) {
        //return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
        return null;
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String content;
        public String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
