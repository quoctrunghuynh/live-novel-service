package com.livenovel.dev.repository.redis;

import com.livenovel.dev.entity.User;
import com.livenovel.dev.payload.user.response.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDtoRedisRepository {
    public final static String KEY = "UserDto";

    private final RedisTemplate<String, Object> template;

    public Object findUserDtoWithId(Long id) {
        return template.opsForHash().get(KEY, id);
    }

    public void saveUserDto(UserDto userDto) {
        template.opsForHash().put(KEY, userDto.getId(), userDto);
    }

    public void updateUserDtoByUser(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        template.opsForHash().put(KEY, userDto.getId(), userDto);
    }

    public void deleteUserDtoByUser(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        template.opsForHash().delete(KEY, userDto.getId());
    }

}
