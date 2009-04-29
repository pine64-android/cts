/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.content.cts;

import android.app.cts.MockActivity;
import android.content.Context;
import android.content.MutableContextWrapper;
import android.test.AndroidTestCase;
import dalvik.annotation.TestInfo;
import dalvik.annotation.TestStatus;
import dalvik.annotation.TestTarget;
import dalvik.annotation.TestTargetClass;

@TestTargetClass(MutableContextWrapper.class)
public class MutableContextWrapperTest extends AndroidTestCase {

    MutableContextWrapper mMutableContextWrapper;
    Context mContext;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMutableContextWrapper = null;
        mContext = getContext();
    }

    @TestInfo(
      status = TestStatus.TBR,
      notes = "",
      targets = {
        @TestTarget(
          methodName = "MutableContextWrapper",
          methodArgs = {Context.class}
        )
    })
    public void testConstructor() {

        mMutableContextWrapper = new MutableContextWrapper(mContext);
        assertNotNull(mMutableContextWrapper);
    }

    @TestInfo(
      status = TestStatus.TBR,
      notes = "",
      targets = {
        @TestTarget(
          methodName = "setBaseContext",
          methodArgs = {Context.class}
        )
    })
    public void testSetBaseContext() {
        mMutableContextWrapper = new MutableContextWrapper(mContext);
        assertTrue(mContext.equals(mMutableContextWrapper.getBaseContext()));
        MockActivity actitity = new MockActivity();
        Context base = actitity;
        mMutableContextWrapper.setBaseContext(base);
        assertTrue(base.equals(mMutableContextWrapper.getBaseContext()));
    }

}
