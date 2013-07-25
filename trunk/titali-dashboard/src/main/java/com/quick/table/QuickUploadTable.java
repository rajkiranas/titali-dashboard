/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.table;

import com.vaadin.data.Property;
import com.vaadin.demo.dashboard.QuickUpload;
import com.vaadin.demo.dashboard.QuickUploadMasterContainer;
import com.vaadin.ui.Table;

/**
 *
 * @author Sonali Sangle
 */
public class QuickUploadTable extends Table {

    private QuickUpload quickUpload;

    public QuickUploadTable(QuickUpload quickUpload) {
        this.quickUpload = quickUpload;

        //setCaption("Uploaded topics");
        //addStyleName("plain");
        //addStyleName("borderless");
        setSortEnabled(true);
        setWidth("100%");
        setPageLength(10);
        setMultiSelect(true);
        setImmediate(true); // react at once when something is selected
        setContainerDataSource(quickUpload.getQuickUploadMasterContainer());
        setVisibleColumns(QuickUploadMasterContainer.NATURAL_COL_ORDER_QUICKUPLOAD_INFO);
        setColumnHeaders(QuickUploadMasterContainer.COL_HEADERS_ENGLISH_QUICKUPLOAD_INFO);

        setSelectable(true);

        addValueChangeListener((Property.ValueChangeListener) quickUpload);

        setValue(firstItemId());

    }
}
