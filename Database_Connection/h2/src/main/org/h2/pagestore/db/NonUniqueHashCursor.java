/*
 * Copyright 2004-2019 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (https://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.pagestore.db;

import org.h2.engine.Session;
import org.h2.index.Cursor;
import org.h2.result.Row;
import org.h2.result.SearchRow;

import java.util.ArrayList;

/**
 * Cursor implementation for non-unique hash index
 *
 * @author Sergi Vladykin
 */
public class NonUniqueHashCursor implements Cursor {

    private final Session session;
    private final ArrayList<Long> positions;
    private final PageStoreTable tableData;

    private int index = -1;

    public NonUniqueHashCursor(Session session, PageStoreTable tableData,
            ArrayList<Long> positions) {
        this.session = session;
        this.tableData = tableData;
        this.positions = positions;
    }

    @Override
    public Row get() {
        if (index < 0 || index >= positions.size()) {
            return null;
        }
        return tableData.getRow(session, positions.get(index));
    }

    @Override
    public SearchRow getSearchRow() {
        return get();
    }

    @Override
    public boolean next() {
        return positions != null && ++index < positions.size();
    }

    @Override
    public boolean previous() {
        return positions != null && --index >= 0;
    }

}
