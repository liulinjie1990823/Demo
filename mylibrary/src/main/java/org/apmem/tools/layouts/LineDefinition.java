package org.apmem.tools.layouts;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

class LineDefinition {
    private final List<View> views = new ArrayList<View>();
    private final org.apmem.tools.layouts.LayoutConfiguration config;
    private final int maxLength;
    private int lineLength;
    private int lineThickness;
    private int lineLengthWithSpacing;
    private int lineThicknessWithSpacing;
    private int lineStartThickness;
    private int lineStartLength;

    public LineDefinition(int maxLength, org.apmem.tools.layouts.LayoutConfiguration config) {
        this.lineStartThickness = 0;
        this.lineStartLength = 0;
        this.maxLength = maxLength;
        this.config = config;
    }

    public void addView(View child) {
        this.addView(this.views.size(), child);
    }

    public void addView(int i, View child) {
        final org.apmem.tools.layouts.FlowLayout.LayoutParams lp = (org.apmem.tools.layouts.FlowLayout.LayoutParams) child.getLayoutParams();

        this.views.add(i, child);

        this.lineLength = this.lineLengthWithSpacing + lp.getLength();
        this.lineLengthWithSpacing = this.lineLength + lp.getSpacingLength();
        this.lineThicknessWithSpacing = Math.max(this.lineThicknessWithSpacing, lp.getThickness() + lp.getSpacingThickness());
        this.lineThickness = Math.max(this.lineThickness, lp.getThickness());
    }

    public boolean canFit(View child) {
        final int childLength;
        if (this.config.getOrientation() == org.apmem.tools.layouts.FlowLayout.HORIZONTAL) {
            childLength = child.getMeasuredWidth();
        } else {
            childLength = child.getMeasuredHeight();
        }
        return lineLengthWithSpacing + childLength <= maxLength;
    }

    public int getLineStartThickness() {
        return lineStartThickness;
    }

    public int getLineThickness() {
        return lineThicknessWithSpacing;
    }

    public int getLineLength() {
        return lineLength;
    }

    public int getLineStartLength() {
        return lineStartLength;
    }

    public List<View> getViews() {
        return views;
    }

    public void setThickness(int thickness) {
        int thicknessSpacing = this.lineThicknessWithSpacing - this.lineThickness;
        this.lineThicknessWithSpacing = thickness;
        this.lineThickness = thickness - thicknessSpacing;
    }

    public void setLength(int length) {
        int lengthSpacing = this.lineLengthWithSpacing - this.lineLength;
        this.lineLength = length;
        this.lineLengthWithSpacing = length + lengthSpacing;
    }

    public void addLineStartThickness(int extraLineStartThickness) {
        this.lineStartThickness += extraLineStartThickness;
    }

    public void addLineStartLength(int extraLineStartLength) {
        this.lineStartLength += extraLineStartLength;
    }
}