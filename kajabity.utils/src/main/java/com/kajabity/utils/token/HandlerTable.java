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
package com.kajabity.utils.token;

/**
 * Creates a table of handlers for each character to categorise each character for tokenisation.
 */
public class HandlerTable {
    /**
     * An array of flags for each character indicating type membership.
     */
    private final int[] masks = new int[256];

    /**
     * An array of handlers for individual characters.
     */
    private final Handler[] handlers = new Handler[256];

    /**
     * An extra handler for end of input and, for simplicity, any invalid
     * character indices.
     */
    private Handler eosHandler = null;

    /**
     * Returned type for invalid or eos inputs.
     */
    private int eosMask = 0;

    public Handler getHandler(int ch) {
        if (ch < 0 || ch >= handlers.length) {
            return eosHandler;
        } else {
            return handlers[ch];
        }
    }

    public boolean isChar(int ch, int mask) {
        if (ch < 0 || ch >= handlers.length) {
            return false;
        } else {
            return (masks[ch] & mask) != 0;
        }
    }

    public void setChar(int ch, Handler handler, int mask) {
        if (ch >= 0 && ch < handlers.length) {
            handlers[ch] = handler;
            masks[ch] |= mask;
        }
    }

    public void setChar(String chars, Handler handler, int mask) {
        if (chars != null) {
            for (int i = 0; i < chars.length(); i++) {
                setChar(chars.charAt(i), handler, mask);
            }
        }
    }

    public void setChar(int chFrom, int chTo, Handler handler, int mask) {
        if (chFrom < 0) {
            chFrom = 0;
        }

        if (chTo >= handlers.length) {
            chTo = handlers.length - 1;
        }

        for (int ch = chFrom; ch <= chTo; ch++) {
            handlers[ch] = handler;
            masks[ch] |= mask;
        }
    }

    public void setEOS(Handler handler, int mask) {
        eosHandler = handler;
        eosMask |= mask;
    }
}
