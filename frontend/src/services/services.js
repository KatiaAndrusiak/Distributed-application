export const postData = async (url, data) => {
    const res = await fetch(url, {
        method: "POST",
        headers: {
            'Content-type': 'application/json'
        },
        body: data // info, that we send
    });

    return await res.json();
};

export const getResource = async (url) => {
    const res = await fetch(url);

    if (!res.ok) {
        throw prompt(new Error(`Could not fetch ${url}, status: ${res.status}`));
    }

    return await res.json();
};

export const validateStringFields = (field) => {
    const regex = /^[a-zA-ZAaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż]*$/;
    if (regex.test(field) && field.length > 0 && field.length < 50) {
        return true;
    }
    return false;
}

export const validatePasswordField = (field) => {
    return field.length < 6 ? false : true
}

export const validateEmailField = (field) => {
    const regex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (regex.test(field) && field.length > 0 && field.length < 50) {
        return true;
    }
    return false;
}

export const validatePhoneField = (field) => {
    const regex = /^\+[48][0-9]{10}$/;
    return regex.test(field) ? true : false;
}

export const validateAdressField = (field) => {
    const regex = /^[a-zA-ZAaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż ]+[,| ]+[0-9]+[a-zA-Z]?/;
    if (regex.test(field)  && field.length > 0) {
        return true;
    }
    return false;
}

export const closeModal = (setter) => {
    setter(false);
}

export const createModalContent = (header, messages) => {
    const tempModalContent = {};
    tempModalContent.header = header;
    tempModalContent.messages = messages;

    return tempModalContent;
}
