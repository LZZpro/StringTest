package org.example.controller;

import org.example.domain.R;
import org.example.service.IStringOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/7/11 10:15
 */
@RestController
public class StringTestController {

    @Resource
    IStringOperation stringOperation;
    //http://localhost:8848/test/getnewstring/aaacbvvbbkl
    @GetMapping("/getnewstring/{input}")
    public R<?> getString(@PathVariable String input)
    {
        return R.ok(stringOperation.deleteConsecutiveCharactersMoreThen3(input));
    }

    //http://localhost:8848/test/getreplacestring/aaacbvvbbkl
    @GetMapping("/getreplacestring/{input}")
    public R<?> getReplaceString(@PathVariable String input)
    {
        return R.ok(stringOperation.replaceConsecutiveByPreAlpha(input));
    }

}
