package com.jvj.test.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiRenderer;

public class UiRenderRatingCell extends AbstractCell<Rating> {

	// This can be compared to UiBinder --> here where all magic is done
	public interface Renderer extends UiRenderer {
		void render(SafeHtmlBuilder sb, String name, SafeHtml star1,
				SafeHtml star2, SafeHtml star3, SafeHtml star4, SafeHtml star5,
				String star1class, String star2class, String star3class,
				String star4class, String star5class);

		void onBrowserEvent(UiRenderRatingCell o, NativeEvent e, Element p,
				ValueUpdater<Rating> v, Context c, Rating n);

		Styles getStyles();
	}

	private static Renderer uiRenderer = GWT.create(Renderer.class);

	public UiRenderRatingCell() {
		super(BrowserEvents.CLICK, BrowserEvents.KEYDOWN);
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, Rating value,
			NativeEvent event, ValueUpdater<Rating> valueUpdater) {

		uiRenderer.onBrowserEvent(this, event, parent, valueUpdater, context,
				value);

	}

	@Override
	public void render(Context context, Rating value,
			SafeHtmlBuilder safeHtmlBuilder) {

		// Label - the easy bit
		String name = value.getLabel();

		// And the stars along with the appropriate style
		SafeHtml[] stars = new SafeHtml[5];
		String[] starClass = new String[5];
		for (int i = 0; i < 5; i++) {
			if (i < value.getRating()) {
				stars[i] = SafeHtmlUtils.fromSafeConstant("&#9733;");
				starClass[i] = uiRenderer.getStyles().ratingStar();
				if (value.getUserRating() != null) {
					if (i < value.getUserRating()) {
						starClass[i] = uiRenderer.getStyles().userRatingStar();
					}
				}
			} else {
				stars[i] = SafeHtmlUtils.fromSafeConstant("☆");
				if (value.getUserRating() != null) {
					starClass[i] = uiRenderer.getStyles().star();
					if (i < value.getUserRating()) {
						starClass[i] = uiRenderer.getStyles().userStar();
					}
				}
			}
		}

		// We directly the uiRenderer and we pass the HtmlBuilder
		uiRenderer.render(safeHtmlBuilder, name, stars[0], stars[1], stars[2],
				stars[3], stars[4], starClass[0], starClass[1], starClass[2],
				starClass[3], starClass[4]);
	}

	@UiHandler({ "star1span" })
	void onClickStar1(ClickEvent event, Element parent,
			ValueUpdater<Rating> valueUpdater, Context context, Rating value) {
		value.setUserRating(1);
		setValue(context, parent.getParentElement(), value);
	}

	@UiHandler({ "star2span" })
	void onClickStar2(ClickEvent event, Element parent,
			ValueUpdater<Rating> valueUpdater, Context context, Rating value) {
		value.setUserRating(2);
		setValue(context, parent.getParentElement(), value);
	}

	@UiHandler({ "star3span" })
	void onClickStar3(ClickEvent event, Element parent,
			ValueUpdater<Rating> valueUpdater, Context context, Rating value) {
		value.setUserRating(3);
		setValue(context, parent.getParentElement(), value);
	}

	@UiHandler({ "star4span" })
	void onClickStar4(ClickEvent event, Element parent,
			ValueUpdater<Rating> valueUpdater, Context context, Rating value) {
		value.setUserRating(4);
		setValue(context, parent.getParentElement(), value);
	}

	@UiHandler({ "star5span" })
	void onClickStar5(ClickEvent event, Element parent,
			ValueUpdater<Rating> valueUpdater, Context context, Rating value) {
		value.setUserRating(5);
		setValue(context, parent.getParentElement(), value);
	}
}