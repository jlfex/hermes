package com.jlfex.hermes.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.model.Text;
import com.jlfex.hermes.repository.TextRepository;
import com.jlfex.hermes.service.TextService;

@Service
@Transactional
public class TextServiceImpl implements TextService {

	@Autowired
	private TextRepository textRepository;

	@Override
	public Text loadByReferenceAndType(String reference, String type) {
		return textRepository.findByReferenceAndType(reference, type);
	}

	@Override
	public Text loadById(String id) {
		return textRepository.findOne(id);
	}

}
