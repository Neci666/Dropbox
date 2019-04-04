/*
 * Copyright © 2018-2019 Jitterbit, Inc.
 *
 * Licensed under the JITTERBIT MASTER SUBSCRIPTION AGREEMENT
 * (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * https://www.jitterbit.com/cloud-eula
 *
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */

package org.jitterbit.connector.dropbox;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.jitterbit.connector.sdk.Connection;

/**
 * Connection to a Dropbox endpoint. Uses the
 * <a href="https://dropbox.github.io/dropbox-sdk-java/api-docs/v2.1.x/"
 * target="_blank">Official Dropbox Java SDK 2.1.2 API</a>.
 */
public class DropboxConnection implements Connection {

  /**
   * Constructs a Dropbox connection using a Dropbox app key and access token.
   *
   * @param appKey Dropbox appKey
   * @param accessToken Dropbox OAuth 2 access token that gives your app the ability to make
   * Dropbox API calls
   * @param locale IETF BCP 47 language tag of locale to use for user-visible text in responses,
   * or null to use the user's Dropbox locale preference
   */
  public DropboxConnection(String appKey, String accessToken, String locale) {
    this.appKey = appKey;
    this.accessToken = accessToken;
    this.locale = locale;
  }

  /**
   * Opens a Dropbox version 2 connection.
   */
  public void open() {
    DbxRequestConfig dbxConfig = new DbxRequestConfig(appKey, locale);
    client = new DbxClientV2(dbxConfig, accessToken);
    System.out.println("Dropbox Connection successful -> access-token: " + accessToken + ", app-key: " +
      appKey);
    client.files();
  }

  /**
   * Returns the Dropbox version 2 client of the connection. If there is no client, opens
   * a new Dropbox connection and returns the client.
   *
   * @return the Dropbox version 2 client
   */
  public DbxClientV2 getClient() {
    if (client == null) {
      open();
    }
    return client;
  }

  /**
   * Closes the Dropbox connection.
   */
  public void close() {
    client = null;
  }

  private DbxClientV2 client;
  private String accessToken;
  private String appKey;
  private String locale;
}
