package com.jvj.test.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesBuilder;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class SafeHtmlRatingCell extends AbstractCell<Rating> {

	public interface Templates extends SafeHtmlTemplates {

		@Template("<div class='rating'>{4}{3}{2}{1}{0}</div>")
		SafeHtml ratings(SafeHtml star4, SafeHtml star3, SafeHtml star2,
				SafeHtml star1, SafeHtml star0);

		@Template("<span class='{0}' style='{1}'>{2}</span>")
		SafeHtml goldStar(String id, SafeStyles style, SafeHtml star);
	}

	private static final Templates TEMPLATES = GWT.create(Templates.class);

	// Initialize and subscribe to BrowserEvents
	public SafeHtmlRatingCell() {
		super(BrowserEvents.CLICK);
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, Rating value,
			NativeEvent event, ValueUpdater<Rating> valueUpdater) {

		// Return if no value
		if (value == null) {
			return;
		}

		// If event was a "CLICK"
		if (BrowserEvents.CLICK.equals(event.getType())) {

			// Find element clicked
			Element e = getElementFromPoint(event.getClientX(),
					event.getClientY());

			// If it was a star
			if (e.getClassName().startsWith("star")) {

				// Determine the star number
				String numStr = e.getClassName().substring(
						e.getClassName().length() - 1);
				int stars = Integer.parseInt(numStr);

				// Update rating and refresh
				value.setUserRating(stars);
				setValue(context, parent, value);
			}
		}

		// Call super to handle standard events like KeyDown
		super.onBrowserEvent(context, parent, value, event, valueUpdater);
	}

	/**
	 * 
	 * Returns the topmost element of from given coordinates.
	 * 
	 * TODO fix crossplat issues clientX vs pageX. See quircksmode. Not critical
	 * for vaadin as we scroll div istead of page.
	 * 
	 * @param x
	 * @param y
	 * @return the element at given coordinates
	 */
	public static native Element getElementFromPoint(int clientX, int clientY)
	/*-{
		var el = $wnd.document.elementFromPoint(clientX, clientY);
		// Call elementFromPoint two times to make sure IE8 also returns something sensible if the application is running in an iframe
		el = $wnd.document.elementFromPoint(clientX, clientY);
		if (el != null && el.nodeType == 3) {
			el = el.parentNode;
		}
		return el;
	}-*/;

	@Override
	public void render(Context context, Rating value, SafeHtmlBuilder sb) {

		// Add label
		sb.appendHtmlConstant("<div style='font-weight: bold; font-size: 15px;'>");
		sb.append(SafeHtmlUtils.fromString(value.getLabel()));
		sb.appendHtmlConstant("</div>");

		// Add stars
		SafeHtml[] stars = new SafeHtml[5];
		for (int i = 0; i < 5; i++) {

			SafeStylesBuilder ssb = new SafeStylesBuilder();
			SafeHtml sh;
			if (i < value.getRating()) {
				ssb.trustedColor("gold");
				sh = SafeHtmlUtils.fromSafeConstant("&#9733;");
			} else {
				ssb.trustedColor("black");
				sh = SafeHtmlUtils.fromSafeConstant("☆");
			}

			if (value.getUserRating() != null) {
				if (i < value.getUserRating()) {
					ssb.appendTrustedString("text-shadow: 0 0 1px #000000;");
				}
			}

			stars[i] = TEMPLATES.goldStar("star_" + (i + 1),
					ssb.toSafeStyles(), sh);
		}

		sb.append(TEMPLATES.ratings(stars[0], stars[1], stars[2], stars[3],
				stars[4]));
	}
}