
package com.sinclair.digital.app.mysql;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.InvalidParameterException;

import org.json.JSONObject;

import org.apache.log4j.Logger;

public class SecretData {
  private final static Logger logger = Logger.getLogger(SecretData.class);

  public String getImportString() {
    String secretName = "mysql_data_migration_import";

    AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
        .withRegion(Regions.US_WEST_2)
          .withCredentials(
            new ProfileCredentialsProvider("default")).build();
          
    GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);

    GetSecretValueResult getSecretValueResponse = null;

    JSONObject secretObject = null;

    try {
      getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
    }

    catch (ResourceNotFoundException e) {
      logger.error("The requested secret " + secretName + " was not found");
    } catch (InvalidRequestException e) {
      logger.error("The request was invalid due to " + e.getMessage());
    } catch (InvalidParameterException e) {
      logger.error("The request had invalid params: " + e.getMessage());
    }
    if (getSecretValueResponse == null) {
      logger.info("null secret response");
    }

    String secret = getSecretValueResponse.getSecretString();

    String retString = null;

    if (secret != null) {
      secretObject = new JSONObject(secret);

      retString = String.format("jdbc:mysql://%s/sinclair?user=%s&password=%s&serverTimezone=UTC", secretObject.getString("host"),
          secretObject.getString("username"), secretObject.getString("password"));
    } else {
      logger.error("The secret is null");
    }

    return retString;

  }

  public String getExportString() {

   String secretName = "mysql_data_migration_export";

   AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
   .withRegion(Regions.US_WEST_2)
     .withCredentials(
       new ProfileCredentialsProvider("default")).build();
  

    
    GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);

    GetSecretValueResult getSecretValueResponse = null;

    JSONObject secretObject = null;

    try {
      getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
    }

    catch (ResourceNotFoundException e) {
      logger.error("The requested secret " + secretName + " was not found");
    } catch (InvalidRequestException e) {
      logger.error("The request was invalid due to " + e.getMessage());
    } catch (InvalidParameterException e) {
      logger.error("The request had invalid params: " + e.getMessage());
    }
    if (getSecretValueResponse == null) {
      logger.info("null secret response");
    }

    String secret = getSecretValueResponse.getSecretString();

    String retString = null;

    if (secret != null) {
      secretObject = new JSONObject(secret);

      retString = String.format("jdbc:mysql://%s/sinclair?user=%s&password=%s&serverTimezone=UTC", secretObject.getString("host"),
          secretObject.getString("username"), secretObject.getString("password"));
    } else {
      logger.error("The secret is null");
    }

    return retString;

  }
}
