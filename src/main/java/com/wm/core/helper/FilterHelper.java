package com.wm.core.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FilterHelper {

    private static final Logger logger = LoggerFactory.getLogger(FilterHelper.class);

    /**
     * this will compile a list of patterns
     * from the supplied string patterns.
     * The returned Pattern object will be used to
     * determine if a filter should run or not
     * @param rawWhiteList
     * @return list of pattern
     */
    public static List<Pattern> compileWhiteListPattern(List<String> rawWhiteList) {
        if(Objects.isNull(rawWhiteList)) return new ArrayList<>();
        return rawWhiteList.parallelStream().map(wl -> {
            wl = wl.replaceAll("\\*", ".*");
            wl = "^"+wl+"$";
            logger.debug("Add to whitelist : " + wl);
            return Pattern.compile(wl);
        }).collect(Collectors.toList());
    }





}
