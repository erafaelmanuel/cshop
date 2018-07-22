package com.rem.cs.web.util;

import org.springframework.data.domain.Page;

public class PageHelper {

    boolean prevEllipsis;
    boolean nextEllipsis;
    final int pages[];

    public PageHelper(int currentPage, Page<?> page) {
        pages = new int[page.getTotalPages() < 5 ? page.getTotalPages() : 5];
        if (pages.length == 5) {
            if (currentPage < pages.length - 1) {
                for (int i = 0; i < pages.length; i++) {
                    pages[i] = i + 1;
                }
                if (page.getTotalPages() > pages.length) {
                    nextEllipsis = true;
                }
            } else {
                final int start = currentPage - 2;

                if (currentPage + 2 >= page.getTotalPages()) {
                    for (int i = 0; i < pages.length; i++) {
                        pages[(pages.length - 1) - i] = page.getTotalPages() - i;
                    }
                    prevEllipsis = true;
                } else {
                    for (int i = 0; i < pages.length; i++) {
                        pages[i] = start + i;
                    }
                    prevEllipsis = true;
                    nextEllipsis = true;
                }
            }
        } else {
            for (int i = 0; i < pages.length; i++) {
                pages[i] = i + 1;
            }
        }
    }

    public boolean hasPrevEllipsis() {
        return prevEllipsis;
    }

    public boolean hasNextEllipsis() {
        return nextEllipsis;
    }

    public int[] getPages() {
        return pages;
    }
}
