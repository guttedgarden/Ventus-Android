package com.ventus.app.tools;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;

public class differentSize {
    public static CharSequence spannableTwoString(String firstString, int firstSize,
                                               String secondString, int secondSize,
                                                String thirdString, int thirdSize){

        SpannableString first = new SpannableString(firstString);
        first.setSpan(new AbsoluteSizeSpan(firstSize),0,firstString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString second = new SpannableString(secondString);
        second.setSpan(new AbsoluteSizeSpan(secondSize),0,secondString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString third = new SpannableString(thirdString);
        third.setSpan(new AbsoluteSizeSpan(thirdSize),0,thirdString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        CharSequence exitSpan = TextUtils.concat(first,"\n",second,third);
        return exitSpan;
    }
}
