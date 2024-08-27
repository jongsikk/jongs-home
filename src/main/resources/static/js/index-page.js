async function fetchMeasurements() {
    try {
        const response = await fetch('/api/v1/entity'); // API 엔드포인트를 여기에 설정
        const entityData = await response.json(); // JSON 데이터로 변환
        const response2 = await fetch('/api/v1/energy/devices'); // API 엔드포인트를 여기에 설정
        const energyData = await response2.json(); // JSON 데이터로 변환
        console.log(energyData);
        console.log(entityData);
        // HTML 업데이트
        updateMeasurementList(entityData, energyData);
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

// HTML 업데이트 함수
function updateMeasurementList(entityList) {
    const container = document.getElementById('main-container');
    container.innerHTML = ''; // 기존 내용을 지웁니다.
    let measurementList = ["kWh", "units", "%"]
    measurementList.forEach(measurement => {
        const measurementDiv = document.createElement('div');
        measurementDiv.innerHTML = `<h3 class="text-lg font-weight-bold mb-3">${measurement}</h3>`;

        const rowDiv = document.createElement('div');
        rowDiv.className = 'row';
        entityList.forEach(entity => {
            if (entity.measurement === measurement) {
                const entityDiv = document.createElement('div');
                entityDiv.className = 'col-xl-3 col-md-6 mb-4';
                entityDiv.innerHTML = `
                    <div class="card border-left-primary shadow h-100 py-2">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">${entity.name}</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">
                                        ${entity.measurement === 'units' ? (entity.value === 1.0 ? 'on' : 'off') : (entity.value + ' ' + entity.measurement)}
                                    </div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-calendar fa-2x text-gray-300"></i>
                                </div>
                            </div>
                        </div>
                    </div>`;
                rowDiv.appendChild(entityDiv);
            }
        });

        measurementDiv.appendChild(rowDiv);
        container.appendChild(measurementDiv);
    });
}

document.addEventListener('DOMContentLoaded', () => {
    fetchMeasurements(); // 초기 데이터 로드
    setInterval(fetchMeasurements, 1000); // 1초마다 데이터 갱신
});