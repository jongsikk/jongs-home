function getEntityValue(entity) {
    if (entity.measurement === 'units') {
        if (entity.domain === 'number') {
            return entity.value;
        } else if (entity.domain === 'binary_sensor') {
            return entity.value === 'on' ? 'open' : 'close';
        } else {
            return entity.state;
        }
    } else {
        return `${entity.value} ${entity.measurement}`;
    }
}

function updateEntityValues() {
    fetch('/api/v1/entity')
        .then(response => response.json())
        .then(data => {
            data.forEach(entity => {
                const valueElement = document.getElementById(`${entity.entityId}:${entity.name}`);
                valueElement.innerText = getEntityValue(entity);
            });
        })
        .catch(error => console.error('Error fetching entity values:', error));
}

document.addEventListener('DOMContentLoaded', () => {
    setInterval(updateEntityValues, 1000);
});
