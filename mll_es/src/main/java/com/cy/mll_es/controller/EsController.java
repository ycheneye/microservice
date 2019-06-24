package com.cy.mll_es.controller;

import com.cy.microserviceapi.result.ResultResponse;
import com.cy.microserviceapi.util.JsonMapper;
import com.cy.mll_es.entity.EsProduct;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @作者 chenyi
 * @date 2019/6/6 15:27
 */
@RestController
@RequestMapping("es")
@Slf4j
public class EsController {

    @Autowired
    private TransportClient transportClient;
    public ResultResponse addProduct(@RequestBody EsProduct esProduct){
        log.info("添加的数据为：{}",esProduct);
        //输入索引 与type名称
        IndexResponse indexResponse =   transportClient.prepareIndex("mll", "product").
                //指定json字符串
                        setSource(JsonMapper.obj2String(esProduct), XContentType.JSON).get();
        //返回查看数据是否成功
        return ResultResponse.success(indexResponse);

    }
}
