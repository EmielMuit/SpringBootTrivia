package com.quad.trivia.triviawebservice.helpers;

public final class Consts {

    //Mappings
    public static final String QUESTIONMAPPING = "/questions";
    public static final String CHECKANSWERSMAPPING = "/checkanswers";

    public static final String CONTENTTYPE = "application/json";

    // Model variables
    public static final String MODELFORMVARIABLECONTENT = "rewrittenContent";
    public static final String MODELFORMVARIABLENAME = "content";

    // Trivia session storage attribute names
    public static final String REWRITTENJSONSESSIONATTRIBUTENAME = "triviajson";
    public static final String ANSWERARRAYSESSIONATTRIBUTENAME = "answers";

    // URLs
    public static final String HOMEPAGE = "index.html";
    public static final String ROOTURL = "/";
    public static final String TRIVIASOURCEURL = "https://opentdb.com/api.php?amount=5";
}
