package com.cy.mll_seckill.controller;

import com.cy.microserviceapi.result.ResultResponse;
import com.cy.microserviceapi.result.SeckillEnum;
import com.cy.mll_seckill.entity.MllSeckillProduct;
import com.cy.mll_seckill.service.MllSeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.UUID;

/**
 * @作者 chenyi
 * @date 2019/6/3 10:08
 */
@Controller
@RequestMapping("seckill")
@Slf4j
public class MllSeckillController {

    @Autowired
    private MllSeckillService seckillService;

    @GetMapping("page/{productId}")
    public ModelAndView page(@PathVariable("productId") Long productId , Map map){
        ResultResponse<MllSeckillProduct> resultResponse = seckillService.findByProdcutId(productId);
        MllSeckillProduct seckillProduct = resultResponse.getData();
        long startTime = seckillProduct.getStartDate().getTime();
        long endGame = seckillProduct.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int seckillStatus = 0;
        int remainSeconds = 0;

        if (now<startTime){
            seckillStatus= SeckillEnum.NONE_BEGIN.getCode();
            remainSeconds= (int) ((startTime-now)/1000);
        }else if (now>endGame){
            seckillStatus=SeckillEnum.ALREADY_DONE.getCode();
            remainSeconds=-1;
        }else {
            seckillStatus=SeckillEnum.KILLING.getCode();
            remainSeconds=0;
        }
        log.info("可抢购状态：{}", seckillStatus);
        map.put("miaoshaStatus", seckillStatus);
        log.info("剩余秒数：{}", remainSeconds);
        map.put("remainSeconds", remainSeconds);
        log.info("产品开始日期信息：{}", seckillProduct.getStartDate().toLocaleString());

        map.put("product",seckillProduct);
        String currentUser = "cheney";
        map.put("user",currentUser);
        return  new ModelAndView("freemarker/seckill",map);

    }

    @PostMapping("doSeckill/{productId}")
    @ResponseBody
    public ResultResponse doSeckill(@PathVariable("productId") Long productId){
        log.info("瞧瞧时谁进来了！" );
        //todo:先判断是否登录
//        String userId = UUID.randomUUID().toString().replace("-", "");
        String userId  = "sherlock";
        return seckillService.doSeckill(productId, userId);
    }
}
