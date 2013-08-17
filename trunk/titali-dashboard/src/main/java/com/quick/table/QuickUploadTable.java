/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.table;

import com.quick.utilities.ConfirmationDialogueBox;
import com.vaadin.data.Property;
import com.vaadin.demo.dashboard.QuickUpload;
import com.vaadin.demo.dashboard.QuickUploadMasterContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;
import java.io.File;

/**
 *
 * @author Sonali Sangle
 */
public class QuickUploadTable extends Table {


    public QuickUploadTable(final QuickUpload quickUpload) {

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
        
        addGeneratedColumn("Remove", new Table.ColumnGenerator() 
            {
            @Override
                public Object generateCell(Table source, Object rowItemBean, Object columnId) 
                {
                    Button btnRemove=new Button("Remove");
                    btnRemove.setImmediate(true);
                    btnRemove.setStyleName(BaseTheme.BUTTON_LINK);
                    setItemData(btnRemove,rowItemBean);
                    quickUpload.addListenertoBtn(btnRemove);               
                    return btnRemove;
                }
            });
        

    }
    public void setItemData(Button btnRemove, Object rowItemBean)
        {
            Object arr[]=new Object[]{this,rowItemBean,""};
            btnRemove.setData(arr);
        }
    
     
}
