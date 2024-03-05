window.onload = (e) => {
    document.getElementById("list_sort").addEventListener("change", (e) => {
        let sortSelect = document.getElementById("list_sort");
        console.log(sortSelect.value);

        location.href = sortSelect.dataset.link + "&sort=" + sortSelect.value;
    });
};
