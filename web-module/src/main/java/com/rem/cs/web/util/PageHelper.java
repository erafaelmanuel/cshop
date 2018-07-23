package com.rem.cs.web.util;

import com.rem.cs.web.dto.Page;

public class PageHelper {

    private boolean prevEllipsis;
    private boolean nextEllipsis;
    private Page[] pages;
    private String[] params;
    private int currentPage;

    private PageHelper(int currentPage, String... params) {
        this.currentPage = currentPage;
        this.params = params;
    }

    public PageHelper(int currentPage, org.springframework.data.domain.Page<?> page, String... params) {
        this(currentPage, params);
        pages = new Page[page.getTotalPages() < 5 ? page.getTotalPages() : 5];
        if (pages.length == 5) {
            if (currentPage < pages.length - 1) {
                for (int i = 0; i < pages.length; i++) {
                    pages[i] = new Page();
                    pages[i].setNumber(i + 1);
                    pages[i].setHref("?page=".concat(String.valueOf(i + 1)));
                    for (String param : params) {
                        pages[i].setHref(pages[i].getHref()
                                .concat("&")
                                .concat(param));
                    }
                }
                if (page.getTotalPages() > pages.length) {
                    nextEllipsis = true;
                }
            } else {
                final int start = currentPage - 2;

                if (currentPage + 2 >= page.getTotalPages()) {
                    for (int i = 0; i < pages.length; i++) {
                        final int index = (5 - 1) - i;
                        pages[index] = new Page();
                        pages[index].setNumber(page.getTotalPages() - i);
                        pages[index].setHref("?page=".concat(String.valueOf(page.getTotalPages() - i)));
                        for (String param : params) {
                            pages[index].setHref(pages[index].getHref()
                                    .concat("&")
                                    .concat(param));
                        }
                    }
                    prevEllipsis = true;
                } else {
                    for (int i = 0; i < pages.length; i++) {
                        pages[i] = new Page();
                        pages[i].setNumber(start + i);
                        pages[i].setHref("?page=".concat(String.valueOf(start + i)));
                        for (String param : params) {
                            pages[i].setHref(pages[i].getHref()
                                    .concat("&")
                                    .concat(param));
                        }
                    }
                    prevEllipsis = true;
                    nextEllipsis = true;
                }
            }
        } else {
            for (int i = 0; i < pages.length; i++) {
                pages[i] = new Page();
                pages[i].setNumber(i + 1);
                pages[i].setHref("?page=".concat(String.valueOf(i + 1)));
                for (String param : params) {
                    pages[i].setHref(pages[i].getHref()
                            .concat("&")
                            .concat(param));
                }
            }
        }
    }

    public boolean hasPrevEllipsis() {
        return prevEllipsis;
    }

    public boolean hasNextEllipsis() {
        return nextEllipsis;
    }

    public Page[] getPages() {
        return pages;
    }

    public Page getPageNext() {
        final Page page = new Page();
        page.setNumber(currentPage + 1);
        page.setHref("?page=".concat(String.valueOf(currentPage + 1)));
        for (String param : params) {
            page.setHref(page.getHref()
                    .concat("&")
                    .concat(param));
        }
        return page;
    }

    public Page getPagePrev() {
        final Page page = new Page();
        page.setNumber(currentPage + 1);
        page.setHref("?page=".concat(String.valueOf(currentPage - 1)));
        for (String param : params) {
            page.setHref(page.getHref()
                    .concat("&")
                    .concat(param));
        }
        return page;
    }
}
