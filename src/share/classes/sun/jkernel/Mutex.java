/*
 * Copyright (c) 2008, 2009, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package sun.jkernel;

/**
 * A mutex which works even between different processes.  Currently implemented
 * only on Win32.
 *
 *@author Ethan Nicholas
 */
public class Mutex {
    static {
        try {
            System.loadLibrary("jkernel");
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }

    private String uniqueId;
    private long handle;

    public static Mutex create(String uniqueId) {
        return new Mutex(uniqueId);
    }


    private Mutex(String uniqueId) {
        this.uniqueId = uniqueId;
        this.handle = createNativeMutex(uniqueId);
    }


    private static native long createNativeMutex(String uniqueId);


    public native void acquire();


    public native boolean acquire(int timeout);


    public native void release();


    public native void destroyNativeMutex();


    public void dispose() {
        destroyNativeMutex();
        handle = 0;
    }


    public void finalize() {
        dispose();
    }


    public String toString() {
        return "Mutex[" + uniqueId + "]";
    }
}
