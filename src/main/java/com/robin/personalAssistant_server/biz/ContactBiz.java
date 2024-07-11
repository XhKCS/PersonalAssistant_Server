package com.robin.personalAssistant_server.biz;

import com.robin.personalAssistant_server.entity.Contact;
import com.robin.personalAssistant_server.mapper.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactBiz {
    @Autowired
    private ContactMapper mapper;

    public void setMapper(ContactMapper mapper) {
        this.mapper = mapper;
    }

    public List<Contact> getContactList() {
        return mapper.selectContacts();
    }

    public Contact getContactById(int contactId) {
        return mapper.selectContactById(contactId);
    }

    public Contact getContactByName(String contactName) {
        return mapper.selectContactByName(contactName);
    }

    public boolean addContact(Contact contact) {
        return mapper.insertContact(contact) > 0;
    }

    public boolean removeContactById(int contactId) {
        return mapper.deleteContactById(contactId) > 0;
    }

    public boolean updateContact(Contact updatedContact) {
        return mapper.updateContact(updatedContact) > 0;
    }
}
