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

import android.content.IntentFilter;
import android.content.IntentFilter.AuthorityEntry;
import android.net.Uri;
import android.test.AndroidTestCase;
import dalvik.annotation.TestInfo;
import dalvik.annotation.TestStatus;
import dalvik.annotation.TestTarget;
import dalvik.annotation.TestTargetClass;

@TestTargetClass(IntentFilter.AuthorityEntry.class)
public class IntentFilter_AuthorityEntryTest extends AndroidTestCase {

    AuthorityEntry mAuthorityEntry;
    final String mHost = "testHost";
    final String mWildHost = "*" + mHost;
    final int mPort = 80;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAuthorityEntry = new AuthorityEntry(mHost, String
                .valueOf(mPort));
    }

   @TestInfo(
      status = TestStatus.TBR,
      notes = "",
      targets = {
        @TestTarget(
          methodName = "IntentFilter.AuthorityEntry",
          methodArgs = {String.class, String.class}
        )
    })
    public void testConstructor() {
        mAuthorityEntry = null;
        mAuthorityEntry = new AuthorityEntry(mHost, String
                .valueOf(mPort));
        assertNotNull(mAuthorityEntry);
        assertEquals(mHost, mAuthorityEntry.getHost());
        assertEquals(mPort, mAuthorityEntry.getPort());

        mAuthorityEntry = new AuthorityEntry(mWildHost, String
                .valueOf(mPort));
        assertNotNull(mAuthorityEntry);
        assertEquals(mWildHost, mAuthorityEntry.getHost());
        assertEquals(Integer.valueOf(mPort).intValue(), mAuthorityEntry
                .getPort());
    }

    @TestInfo(
      status = TestStatus.TBR,
      notes = "",
      targets = {
        @TestTarget(
          methodName = "getPort",
          methodArgs = {}
        ),
        @TestTarget(
          methodName = "getHost",
          methodArgs = {}
        )
    })
    public void testAuthorityEntryProperties() {

        assertEquals(Integer.valueOf(mPort).intValue(), mAuthorityEntry
                .getPort());
        assertEquals(mHost, mAuthorityEntry.getHost());
    }

    @TestInfo(
      status = TestStatus.TBR,
      notes = "",
      targets = {
        @TestTarget(
          methodName = "match",
          methodArgs = {Uri.class}
        )
    })
    public void testMatch() {
        Uri uri = Uri.parse("testUri");
        assertEquals(IntentFilter.NO_MATCH_DATA, mAuthorityEntry.match(uri));
        uri = Uri.parse("content://contacts/deleted_people");
        assertEquals(IntentFilter.NO_MATCH_DATA, mAuthorityEntry.match(uri));
        uri = Uri.parse("test");
        mAuthorityEntry = new IntentFilter.AuthorityEntry(mWildHost, String
                .valueOf(-1));
        assertEquals(IntentFilter.NO_MATCH_DATA, mAuthorityEntry.match(uri));
        uri = Uri.parse("http://" + mHost);
        mAuthorityEntry = new IntentFilter.AuthorityEntry(mHost, String
                .valueOf(-1));
        assertEquals(IntentFilter.MATCH_CATEGORY_HOST, mAuthorityEntry
                .match(uri));

        uri = Uri.parse("http://" + mHost + ":90");
        mAuthorityEntry = new AuthorityEntry(mHost, String
                .valueOf(-1));
        assertEquals(IntentFilter.MATCH_CATEGORY_HOST, mAuthorityEntry
                .match(uri));

        uri = Uri.parse("http://" + mHost + ":80");
        mAuthorityEntry = new AuthorityEntry(mHost, String
                .valueOf(mPort));
        assertEquals(IntentFilter.MATCH_CATEGORY_PORT, mAuthorityEntry
                .match(uri));

        uri = Uri.parse("http://" + mHost + ":80");
        mAuthorityEntry = new AuthorityEntry(mHost, String
                .valueOf(-1));
        assertEquals(IntentFilter.MATCH_CATEGORY_HOST, mAuthorityEntry
                .match(uri));

    }
}
