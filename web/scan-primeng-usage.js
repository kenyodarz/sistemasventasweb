const fs = require("fs");
const path = require("path");

const PRIMENG_COMPONENTS = {
  "p-toast": "ToastModule",
  "p-confirmdialog": "ConfirmDialogModule",
  "p-menu": "MenuModule",
  "p-menubar": "MenubarModule",
  "p-panel": "PanelModule",
  "p-card": "CardModule",
  "p-table": "TableModule",
  "p-toolbar": "ToolbarModule",
  "p-fileupload": "FileUploadModule",
  "p-paginator": "PaginatorModule",
  "p-inputtext": "InputTextModule",
  "p-inputnumber": "InputNumberModule",
  "p-chip": "ChipModule",
  "p-message": "MessageModule",
  "p-messages": "MessagesModule",
  "p-button": "ButtonModule",
  "p-ripple": "RippleModule",
  "p-dialog": "DialogModule",
};

const componentsDir = "./src/app/pages"; // <-- ajusta si tu estructura es diferente

function scanHtmlFile(filePath) {
  const content = fs.readFileSync(filePath, "utf-8");
  const usedTags = new Set();

  Object.keys(PRIMENG_COMPONENTS).forEach((tag) => {
    if (content.includes(`<${tag}`)) {
      usedTags.add(tag);
    }
  });

  return [...usedTags];
}

function suggestImports(htmlPath) {
  const usedTags = scanHtmlFile(htmlPath);
  return usedTags.map((tag) => PRIMENG_COMPONENTS[tag]).filter(Boolean);
}

function scanDirectory(dirPath) {
  const entries = fs.readdirSync(dirPath);

  entries.forEach((entry) => {
    const fullPath = path.join(dirPath, entry);
    if (fs.statSync(fullPath).isDirectory()) {
      scanDirectory(fullPath);
    } else if (entry.endsWith(".component.html")) {
      const imports = suggestImports(fullPath);
      console.log(`📁 ${entry}`);
      console.log(`👉  Sugerido: [ ${imports.join(", ")} ]\n`);
    }
  });
}

scanDirectory(componentsDir);
