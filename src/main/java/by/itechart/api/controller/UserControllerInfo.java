package by.itechart.api.controller;

import by.itechart.api.dto.CreateUserDTO;
import by.itechart.api.dto.UpdateUserDTO;
import by.itechart.api.dto.UserDTO;
import by.itechart.api.util.annotation.ApiPageable;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

public interface UserControllerInfo {

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @ApiOperation(value = "Get all active users", notes = "This method will return all active users")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully returned all active users"),
            @ApiResponse(code = 401, message = "Unauthorized to perform this operation"),
            @ApiResponse(code = 403, message = "Forbidden access to resource"),
            @ApiResponse(code = 500, message = "Server error. Something went wrong")})
    @ApiPageable
    ResponseEntity<List<UserDTO>> getActiveUsers(@ApiParam(value = "Pagination information which can be defined with page, page size and sort") @ApiIgnore Pageable pageable,
                                                 @ApiParam(value = "Specific keyword for filtering. Can be empty")
                                                         String keyword);


    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @ApiOperation(value = "Get all users", notes = "This method will return all users")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully returned all users"),
            @ApiResponse(code = 401, message = "Unauthorized to perform this operation"),
            @ApiResponse(code = 403, message = "Forbidden access to resource"),
            @ApiResponse(code = 500, message = "Server error. Something went wrong")})
    @ApiPageable
    ResponseEntity<List<UserDTO>> getAllUsers(@ApiParam(value = "Pagination information which can be defined with page, page size and sort") @ApiIgnore Pageable pageable,
                                              @ApiParam(value = "Specific keyword for filtering. Can be empty")
                                                      String keyword);

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @ApiOperation(value = "Get current user", notes = "This method will return current user")
    @ApiResponses(value = {@ApiResponse(code = 302, message = "Successfully returned current user"),
            @ApiResponse(code = 401, message = "Unauthorized to perform this operation"),
            @ApiResponse(code = 403, message = "Forbidden access to resource"),
            @ApiResponse(code = 500, message = "Server error. Something went wrong")})
    ResponseEntity<UserDTO> getCurrentUser(Authentication authentication);

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @ApiOperation(value = "Update user partially or with full changes", notes = "This method is used to change information in" +
            "user depending on user's id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated user"),
            @ApiResponse(code = 401, message = "Unauthorized to perform this operation"),
            @ApiResponse(code = 403, message = "Forbidden access to resource"),
            @ApiResponse(code = 500, message = "Server error. Something went wrong")})
    ResponseEntity<UserDTO> updateUser(@ApiParam(value = "id for user update", required = true)
                                               Long id,
                                       @ApiParam(value = "user information for update", required = true)
                                               UpdateUserDTO user);

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @ApiOperation(value = "Update current user information partially or with full changes",
            notes = "This method is used to change information in current user authenticated user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated user"),
            @ApiResponse(code = 401, message = "Unauthorized to perform this operation"),
            @ApiResponse(code = 403, message = "Forbidden access to resource"),
            @ApiResponse(code = 500, message = "Server error. Something went wrong")})
    ResponseEntity<UserDTO> updateCurrentUser(Authentication authentication,
                                              @ApiParam(value = "user information for update", required = true)
                                                      UpdateUserDTO user);

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @ApiOperation(value = "Create new user", notes = "This method is used to create new user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully created new user"),
            @ApiResponse(code = 401, message = "Unauthorized to perform this operation"),
            @ApiResponse(code = 403, message = "Forbidden access to resource"),
            @ApiResponse(code = 500, message = "Server error. Something went wrong")})
    ResponseEntity<UserDTO> createUser(@ApiParam(value = "User information for creation", required = true)
                                               CreateUserDTO user);

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @ApiOperation(value = "Delete user", notes = "This method is used to delete user depending on user's id")
    ResponseEntity<UserDTO> deleteUser(@ApiParam(value = "Id for user deletion", required = true)
                                               Long id);
}
