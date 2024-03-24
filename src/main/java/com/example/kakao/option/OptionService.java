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

    private final OptionJPARepository optionJPARepository;

    @Transactional(readOnly = true)
    public List<OptionResponse.ProductOptionDTO> getProductOptions(int productId){
        List<OptionResponse.ProductOptionDTO> optionList = optionJPARepository.findByProductId(productId)
                .stream().map(OptionResponse.ProductOptionDTO::new)
                .collect(Collectors.toList());;
        return optionList;
    }

    @Transactional(readOnly = true)
    public List<OptionResponse.FindAllDTO> findAll() {
        List<OptionResponse.FindAllDTO> optionList = optionJPARepository.findAll()
                .stream().map(OptionResponse.FindAllDTO::new)
                .collect(Collectors.toList());
        return optionList;
    }
}
