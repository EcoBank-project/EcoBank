/**
 * sectioncontrol.js
 */

$(document).ready(function() {
    initializeSection();
});

function initializeSection() {
    const $container = $('#container');
    const width = $container.width();
    const height = 400;

    const stage = new Konva.Stage({
        container: 'container',
        width: width,
        height: height
    });

    const layer = new Konva.Layer();
    stage.add(layer);

    const amplitude = 20;
    const path = new Konva.Path({
        fill: '#00bfff',
        stroke: '',
        strokeWidth: 0
    });

    layer.add(path);

    const simplex = new SimplexNoise();
    let offset = 0;

    function getWavePath() {
        let wavePath = 'M 0 ' + (height / 2);

        for (let x = 0; x < width; x++) {
            const noiseValue = simplex.noise2D(x * 0.01, offset);
            const y = amplitude * noiseValue + (height / 2);
            wavePath += ' L ' + x + ' ' + y;
        }

        wavePath += ` L ${width} ${height} L 0 ${height} Z`;

        return wavePath;
    }

    function animate() {
        offset += 0.007; // 속도를 높입니다.
        path.data(getWavePath());
        layer.batchDraw();
        requestAnimationFrame(animate);
    }

    animate();
}
