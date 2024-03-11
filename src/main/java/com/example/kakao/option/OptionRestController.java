package com.example.kakao.option;

import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OptionRestController {

    @Autowired
    private OptionService optionService;

    /**
     * @param id
     * ProductId에 해당하는 Option 조회
     * @return
     * 성공 시 Option 리스트 반환
     */
     @GetMapping("/products/{id}/options")
    public ResponseEntity<?> getProductOptions(@PathVariable("id") int id){
         List<OptionResponse> optionResponses = optionService.getOptions(id);
         return ResponseEntity.ok().body(optionResponses);
     }


    /**
     * @return
     * Option 전체 반환
     */
     @GetMapping("/options")
    public List<OptionResponse> getAllOptions(){
         return optionService.findAll();
     }

}
