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

let res = "";

for (let i = 1; i < images.length; i++) {
  const data = JSON.parse(fs.readFileSync(`./res/${images[i]}`, "utf-8"));
  res += `fun frame${i}(){`;

  res += "}\n";
}
