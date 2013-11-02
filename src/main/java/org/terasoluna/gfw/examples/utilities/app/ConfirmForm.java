package org.terasoluna.gfw.examples.utilities.app;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

public class ConfirmForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @AssertTrue
    private boolean consent;

    public boolean isConsent() {
        return consent;
    }

    public void setConsent(boolean consent) {
        this.consent = consent;
    }

}
