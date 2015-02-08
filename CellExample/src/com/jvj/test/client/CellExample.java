package com.jvj.test.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class CellExample implements EntryPoint {

	// Create list of Rating objects to present
	private static final List<Rating> RATINGS = Arrays.asList(new Rating(
			"Police Academy 1", 100, 400), new Rating("Police Academy 2", 50,
			199), new Rating("Police Academy 3", 100, 220));

	interface CellExampleUiBinder extends
			UiBinder<HorizontalPanel, CellExample> {
	}

	private static final CellExampleUiBinder binder = GWT
			.create(CellExampleUiBinder.class);

	@UiField
	HTMLPanel basicContainer, uiRendererContainer;

	@UiField
	Label info;

	public void onModuleLoad() {

		// Create different cells to render each value.
		UiRenderRatingCell uiCell = new UiRenderRatingCell();
		SafeHtmlRatingCell shCell = new SafeHtmlRatingCell();

		// Use the cell renders in cellLists
		CellList<Rating> uiCellList = new CellList<Rating>(uiCell);
		CellList<Rating> shCellList = new CellList<Rating>(shCell);

		// Push data into the widget
		uiCellList.setRowData(RATINGS);
		shCellList.setRowData(RATINGS);

		// Add it to the root panel
		RootLayoutPanel root = RootLayoutPanel.get();
		root.clear();
		root.add(binder.createAndBindUi(this));

		basicContainer.add(shCellList);
		uiRendererContainer.add(uiCellList);
	}
}
