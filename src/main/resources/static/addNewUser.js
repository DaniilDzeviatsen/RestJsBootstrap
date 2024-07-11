async function getRoles() {
    const response = await fetch("/api/profile/roles");
    return await response.json();
}

async function createNewUser(user) {
    const response = await fetch("/api/users", {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(user)
    });

    if (!response.ok) {
        const errorData = await response.json();
        return {success: false, errors: errorData};
    }

    return {success: true};
}

async function addNewUserForm() {
    const newUserForm = document.getElementById("addNewUser");

    newUserForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        const name = newUserForm.querySelector("#usernameNew").value.trim();
        const surname = newUserForm.querySelector("#surnameNew").value.trim();
        const password = newUserForm.querySelector("#passwordNew").value.trim();
        const rolesSelected = document.getElementById("rolesNew");

        let allRoles = await getRoles();
        let AllRoles = {};
        for (let role of allRoles) {
            AllRoles[role.name] = role.id;
        }
        let roles = [];
        for (let option of rolesSelected.selectedOptions) {
            if (Object.keys(AllRoles).indexOf(option.value) !== -1) {
                roles.push({roleId: AllRoles[option.value], name: option.value});
            }
        }

        const newUserData = {
            username: name,
            surname: surname,
            password: password,
            roles: roles
        };

        const result = await createNewUser(newUserData);

        if (result.success) {
            newUserForm.reset();
            document.querySelector('#admin-users-table-tab').click();
            await fillTableOfAllUsers();
        }
    });
}



