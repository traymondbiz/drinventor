/*
 * ******************************************************************************************************
 * Dr. Inventor Text Mining Framework Java Library
 * 
 * This code has been developed by the Natural Language Processing Group of the
 * Universitat Pompeu Fabra in the context of the FP7 European Project Dr. Inventor
 * Call: FP7-ICT-2013.8.1 - Agreement No: 611383
 * 
 * Dr. Inventor Text Mining Framework Java Library is available under an open licence, GPLv3, for non-commercial applications.
 * ******************************************************************************************************
 */
package edu.upf.taln.dri.lib.exception;

/**
 * Exception related to the access of files and resources.
 * 
 *
 */
public class ResourceAccessException extends DRIexception {
	
	private static final long serialVersionUID = 1L;

	public ResourceAccessException(String message) {
        super(message);
    }
	
}
