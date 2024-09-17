import fs from "fs";

const images = fs
  .readdirSync("./res")
  .filter((x) => x.endsWith(".json"))
  .sort((a, b) => {
    const anum = parseInt(a.split(".")[0]);
    const bnum = parseInt(b.split(".")[0]);
    return anum - bnum;
  });

console.log(images);

let res: any[] = [];

for (let i = 1; i < images.length; i++) {
  const data = JSON.parse(fs.readFileSync(`./res/${images[i]}`, "utf-8"));
  const ndata = data
    .map((x: any) => {
      return (
        String.fromCharCode(x[0]) +
        String.fromCharCode(x[1]) +
        String.fromCharCode(x[2] ? 1 : 0)
      );
      if (x[2] == true) return [x[0], x[1], 1];
      else return [x[0], x[1], 0];
    })
    .join("");
  await Bun.write(`./resx/${i}.bin`, ndata);
}

// Bun.write(`./res.json`, JSON.stringify(res));
