package com.hellFire.QuizGame.mapper;

import com.hellFire.QuizGame.dto.UserDto;
import com.hellFire.QuizGame.dto.request.SignUpRequest;
import com.hellFire.QuizGame.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IUserMapper {
    UserDto toDto(User user);
    User createEntity(SignUpRequest request);
}
