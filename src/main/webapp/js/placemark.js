ymaps.ready(init);



function init() {
    var myMap = new ymaps.Map('map', {
        center: [53.898425, 27.55844],
        zoom: 12,
        searchControlProvider: 'yandex#search'
    }),

        objectManager = new ymaps.ObjectManager({
            clusterize: false,
            gridSize: 32,
            clusterDisableClickZoom: true
        });

    myMap.geoObjects.add(objectManager);

    $.ajax({
        url: "js/data.json"
    }).done(function (data) {
        objectManager.add(data);
    });

}

