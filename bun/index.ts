import fs from "fs";
import imgx from "get-pixels";
import path from "path";

const images = fs
  .readdirSync("./images")
  .filter((x) => x.endsWith(".png"))
  .sort((a, b) => {
    const anum = parseInt(a.split(".")[0]);
    const bnum = parseInt(b.split(".")[0]);
    return anum - bnum;
  });

const img = (path: string) => {
  return new Promise<any>((resolve, reject) => {
    imgx(path, (err, data) => {
      if (err) {
        reject(err);
      } else {
        const ndata: boolean[] = [];
        for (
          let i = 0;
          i < data.shape[0] * data.shape[1] * data.shape[2];
          i += 4
        ) {
          const r = data.data[i];
          const g = data.data[i + 1];
          const b = data.data[i + 2];
          const a = data.data[i + 3];
          const brightness = (r + g + b) / 3;
          ndata.push(brightness >= 128);
        }
        resolve([ndata, data.shape[0]]);
      }
    });
  });
};

let lastData = await img(`./images/${images[0]}`);

for (let i = 1; i < images.length; i++) {
  const image = images[i];
  const colors = await img(`./images/${image}`);
  const diff = [];
  for (let j = 0; j < lastData.length; j++) {
    if (lastData[j] !== colors[0][j]) {
      diff.push([j % colors[1], Math.floor(j / colors[1]), colors[0][j]]);
    }
  }
  console.log(i, diff.length);
  lastData = colors[0];

  await Bun.write(`./res/${i}.json`, JSON.stringify(diff));
}
