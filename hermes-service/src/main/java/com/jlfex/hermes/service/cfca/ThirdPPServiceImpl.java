package com.jlfex.hermes.service.cfca;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cfca.payment.api.system.TxMessenger;
import cfca.payment.api.tx.Tx1361Request;
import cfca.payment.api.tx.Tx1361Response;
import cfca.payment.api.tx.Tx1362Request;
import cfca.payment.api.tx.Tx1362Response;

/**
 * 中金支付接口实现
 * 
 * @author wujinsong
 *
 */
@Service
@Transactional
public class ThirdPPServiceImpl implements ThirdPPService {

	@Override
	public Tx1361Response invokeTx1361(Tx1361Request request) {
		try {
			request.process();

			TxMessenger messenger = new TxMessenger();
			String[] respMsg = messenger.send(request.getRequestMessage(), request.getRequestSignature());

			Tx1361Response response = new Tx1361Response(respMsg[0], respMsg[1]);

			return response;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Tx1362Response invokeTx1362(Tx1362Request request) {
		try {
			request.process();

			TxMessenger messenger = new TxMessenger();
			String[] respMsg;
			respMsg = messenger.send(request.getRequestMessage(), request.getRequestSignature());
			Tx1362Response response = new Tx1362Response(respMsg[0], respMsg[1]);

			return response;
		} catch (Exception e) {
			return null;
		}
	}
}
