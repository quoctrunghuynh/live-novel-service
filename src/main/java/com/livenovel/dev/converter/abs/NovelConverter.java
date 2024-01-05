package com.livenovel.dev.converter.abs;

import com.livenovel.dev.converter.GeneralConverter;
import com.livenovel.dev.entity.Novel;
import com.livenovel.dev.payload.novel.NovelDto;
import com.livenovel.dev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelConverter implements GeneralConverter<Novel, NovelDto> {

    private final UserRepository userRepository;

    @Override
    public NovelDto convert(Novel source) {
        NovelDto target = new NovelDto();
        BeanUtils.copyProperties(source, target);
        target.setUpdateAt(source.getUpdateAt());
        target.setCreatedAt(source.getCreatedAt());
        target.setUserId(source.getUser().getId());
        return target;
    }

    @Override
    public Novel revert(NovelDto target) {
        Novel source = new Novel();
        BeanUtils.copyProperties(target, source);
        return source;
    }

    @Override
    public List<NovelDto> convert(List<Novel> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<Novel> revert(List<NovelDto> targets) {
        return targets.stream().map(this::revert).toList();
    }
}
