async function deleteUserData(id) {
    await fetch(`/api/users/${id}`, {method: 'DELETE'});
}

const modalDelete = document.getElementById("deleteModal");
let userIdToDelete = null;

async function DeleteModalHandler() {
    await fillModal(modalDelete);
}

modalDelete.addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    userIdToDelete = button.getAttribute('data-user-id');
});

const formDelete = document.getElementById("formDeleteUser");
formDelete.addEventListener("submit", async function (event) {
        event.preventDefault();
        await deleteUserData(userIdToDelete);
        await fillTableOfAllUsers();
        const modalBootstrap = bootstrap.Modal.getInstance(modalDelete);
        modalBootstrap.hide();
    }
)