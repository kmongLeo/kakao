package com.example.kakao.option;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OptionService {

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Transactional(readOnly = true)
    public List<OptionResponse> getOptions(int productId){
        List<Option> optionList = optionJPARepository.findByProductId(productId);
        return optionList.stream().map(OptionResponse::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OptionResponse> findAll() {
        List<Option> optionList = optionJPARepository.findAll();
        return optionList.stream().map(OptionResponse::of).collect(Collectors.toList());
    }
}
