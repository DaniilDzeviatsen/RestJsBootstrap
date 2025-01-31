async function getUserDataById(id) {
    const response = await fetch(`/api/users/${id}`);
    return await response.json();
}

async function fillModal(modal) {

    modal.addEventListener("show.bs.modal", async function (event) {
        console.log(event.relatedTarget);

        const userId = event.relatedTarget.dataset.userId;
        const user = await getUserDataById(userId);

        const modalBody = modal.querySelector(".modal-body");


        const usernameInput = modalBody.querySelector("input[data-user-id='username']");
        const surnameInput = modalBody.querySelector("input[data-user-id='surname']");
        const passwordInput = modalBody.querySelector("input[data-user-id='password']");
        if (passwordInput !== null) {
            passwordInput.value = user.password;
        }
        usernameInput.value = user.username;
        surnameInput.value = user.surname;

        let rolesSelect = HTMLSelectElement;

        let rolesSelectDelete = modalBody.querySelector("select[data-user-id='allRolesDelete']");
        let rolesSelectEdit = modalBody.querySelector("select[data-user-id='allRolesEdit']");
        let userRolesHTML = "";

        if (rolesSelectDelete !== null) {
            rolesSelect = rolesSelectDelete;
            for (let i = 0; i < user.roles.length; i++) {
                userRolesHTML +=
                    `<option value="${user.roles[i].name}">${user.roles[i].name
                        .substring(5).concat(" ").toString().replaceAll(",", "")}</option>`;
            }
        } else if (rolesSelectEdit !== null) {
            rolesSelect = rolesSelectEdit;
            userRolesHTML +=
                `<option value="ROLE_USER">USER</option>
                 <option value="ROLE_ADMIN">ADMIN</option>`
        }

        rolesSelect.innerHTML = userRolesHTML;
    })
}