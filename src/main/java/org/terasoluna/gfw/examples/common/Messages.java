package org.terasoluna.gfw.examples.common;

import org.terasoluna.gfw.common.message.ResultMessage;

public enum Messages {

    /**
     * [class] pagination<br>
     * [code] i.ex.pa.0001<br>
     * [arguments] none.
     */
    PA_DATE_NOT_FOUND("i.ex.pa.0001"),
    /**
     * [class] sequencer<br>
     * [code] i.ex.se.0001<br>
     * [arguments] {0} article id
     */
    SE_ARTICLE_CREATED("i.ex.se.0001"),
    /**
     * [class] upload<br>
     * [code] i.ex.up.0001<br>
     * [arguments] none.
     */
    UP_FILE_UPLOADED("i.ex.up.0001"),
    /**
     * [class] utilities<br>
     * [code] i.ex.ut.0001<br>
     * [arguments] article id
     */
    UT_ARTICLE_CREATED("i.ex.ut.0001"),
    /**
     * [class] common<br>
     * [code] e.ex.fw.7003<br>
     * [arguments] none.
     */
    FW_INVALID_REQUEST("e.ex.fw.7003");

    private String code;

    private Messages(String code) {
        this.code = code;
    }

    public ResultMessage getResultMessage(Object... args) {
        return ResultMessage.fromCode(code, args);
    }

}
