package by.itechart.api.controller;

import by.itechart.api.dto.CreateUserDTO;
import by.itechart.api.dto.UpdateUserDTO;
import by.itechart.api.dto.UserDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserControllerInfo {
    @ApiOperation(value = "Get all users", notes = "This method will return all users from DB")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully returned all users"),
            @ApiResponse(code = 401, message = "Unauthorized to perform this operation"),
            @ApiResponse(code = 403, message = "Forbidden access to resource"),
            @ApiResponse(code = 500, message = "Server error. Something went wrong")})
    ResponseEntity<List<UserDTO>> getAllUsers();

    @ApiOperation(value = "Get current user", notes = "This method will return current user")
    @ApiResponses(value = {@ApiResponse(code = 302, message = "Successfully returned current user"),
            @ApiResponse(code = 401, message = "Unauthorized to perform this operation"),
            @ApiResponse(code = 403, message = "Forbidden access to resource"),
            @ApiResponse(code = 500, message = "Server error. Something went wrong")})
    ResponseEntity<UserDTO> getCurrentUser();

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

    @ApiOperation(value = "Create new user", notes = "This method is used to create new user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully created new user"),
            @ApiResponse(code = 401, message = "Unauthorized to perform this operation"),
            @ApiResponse(code = 403, message = "Forbidden access to resource"),
            @ApiResponse(code = 500, message = "Server error. Something went wrong")})
    ResponseEntity<UserDTO> createUser(@ApiParam(value = "User information for creation", required = true)
                                               CreateUserDTO user);

    @ApiOperation(value = "Delete user", notes = "This method is used to delete user depending on user's id")
    ResponseEntity<UserDTO> deleteUser(@ApiParam(value = "Id for user deletion", required = true)
                                       Long id);
}
