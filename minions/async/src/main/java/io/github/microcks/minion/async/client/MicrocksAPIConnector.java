/*
 * Licensed to Laurent Broudoux (the "Author") under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Author licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.github.microcks.minion.async.client;

import io.github.microcks.domain.Service;
import io.github.microcks.domain.ServiceView;
import io.github.microcks.domain.TestCaseResult;

import io.github.microcks.minion.async.client.dto.TestCaseReturnDTO;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import java.util.List;

@Path("/api")
@RegisterRestClient
@RegisterClientHeaders
/**
 * REST Client interface for calling various Microcks APIs.
 * @author laurent
 */
public interface MicrocksAPIConnector {

   /**
    * Retrieve the Keycloak server configuration for the Microcks instance.
    * @return The Keycloak config object
    */
   @GET
   @Path("/keycloak/config")
   @Produces("application/json")
   KeycloakConfig getKeycloakConfig();

   /**
    * Retrieve a list of services from Microcks APIs.
    * @param authorization The Authorization header containing the OAuth access token for this API call
    * @param page The page of service list to request
    * @param size The size of the page to fetch
    * @return A list of Service
    */
   @GET
   @Path("/services")
   @Produces("application/json")
   List<Service> listServices(@HeaderParam("Authorization") String authorization, @QueryParam("page") int page,
                              @QueryParam("size") int size);

   /**
    * Retrieve the complete ServiceView for a Service that may contain messages definitions.
    * @param authorization The Authorization header containing the OAuth access token for this API call
    * @param serviceId The unique identifier of Service to get the view for
    * @param messages Whether to include full descriptions of operations messages
    * @return A list of ServiceViewDTO
    */
   @GET
   @Path("/services/{id}")
   @Produces("application/json")
   ServiceView getService(@HeaderParam("Authorization") String authorization, @PathParam("id") String serviceId,
                                   @QueryParam("messages") boolean messages);

   /**
    * Report a TestCaseResult associated to a TestResult.
    * @param testResultId The unique identifier of TestResult we want to report a result for
    * @param testCaseReturn A Test Case return data object for this TestResult
    * @return The created TestCaseResult following reporting
    */
   @POST
   @Path("/tests/{id}/testCaseResult")
   @Produces("application/json")
   TestCaseResult reportTestCaseResult(@PathParam("id") String testResultId, TestCaseReturnDTO testCaseReturn);
}
