package com.example.toolschallanger.TransacaoService;

import com.example.toolschallanger.Dtos.TransacaoRecordDto;
import com.example.toolschallanger.models.TransacaoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoModel transacaoModel;

    public void setAtributosDtoModel(TransacaoRecordDto transacaoRecordDto) {
        BeanUtils.copyProperties(transacaoRecordDto, transacaoModel);
        transacaoModel.setDescricaoModel(transacaoRecordDto.transacaoModel().getDescricaoModel());
        transacaoModel.setFormaPagamentoModel(transacaoRecordDto.transacaoModel().getFormaPagamentoModel());
    }



}
