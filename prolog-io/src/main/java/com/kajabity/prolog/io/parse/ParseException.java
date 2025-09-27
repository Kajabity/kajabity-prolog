/*
 * Copyright (c) 2003-2025 Simon J. Williams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package com.kajabity.prolog.io.parse;

import com.kajabity.prolog.core.environment.PrologException;

/**
 * DOCUMENT ME!
 *
 * @author Simon
 */
public class ParseException extends PrologException {
	/**
     *
     */
    private static final long serialVersionUID = -3067610104849894102L;

    /**
	 * Constructor for ParseException.
	 */
	public ParseException() {
		super();
	}

	/**
	 * Constructor for ParseException.
	 *
	 * @param message
	 */
	public ParseException(String message) {
		super(message);
	}

	/**
	 * Constructor for ParseException.
	 *
	 * @param message
	 * @param cause
	 */
	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor for ParseException.
	 *
	 * @param cause
	 */
	public ParseException(Throwable cause) {
		super(cause);
	}
}
